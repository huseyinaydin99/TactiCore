package tr.com.huseyinaydin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.dto.match.CreateMatchDto;
import tr.com.huseyinaydin.dto.match.GetMatchByIdDto;
import tr.com.huseyinaydin.dto.match.ResultMatchDetailDto;
import tr.com.huseyinaydin.dto.match.ResultMatchDto;
import tr.com.huseyinaydin.dto.match.UpdateMatchDto;
import tr.com.huseyinaydin.entity.Match;
import tr.com.huseyinaydin.entity.Team;
import tr.com.huseyinaydin.entity.enums.MatchStatus;
import tr.com.huseyinaydin.exception.ResourceNotFoundException;
import tr.com.huseyinaydin.mapper.MatchMapper;
import tr.com.huseyinaydin.repository.MatchRepository;
import tr.com.huseyinaydin.repository.TeamRepository;
import tr.com.huseyinaydin.service.MatchService;
import tr.com.huseyinaydin.service.business.MatchEnricher;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final MatchMapper matchMapper;

    private Map<String, Team> loadTeamMap(List<Match> matches) {
        Set<String> ids = new HashSet<>();
        matches.forEach(m -> {
            ids.add(m.getHomeTeamId());
            ids.add(m.getAwayTeamId());
        });
        return teamRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(Team::getId, t -> t));
    }

    private List<ResultMatchDto> toEnrichedList(List<Match> matches) {
        Map<String, Team> teamMap = loadTeamMap(matches);
        return matches.stream()
                .map(match -> {
                    ResultMatchDto dto = matchMapper.toResultDto(match);
                    MatchEnricher.enrich(dto,
                            teamMap.getOrDefault(match.getHomeTeamId(), new Team()),
                            teamMap.getOrDefault(match.getAwayTeamId(), new Team()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Cacheable(value = "matches", key = "'all'")
    public List<ResultMatchDto> getAll() {
        return toEnrichedList(matchRepository.findAll());
    }

    @Cacheable(value = "matches", key = "#id")
    public GetMatchByIdDto getById(String id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + id));
        GetMatchByIdDto dto = matchMapper.toGetByIdDto(match);
        Team homeTeam = teamRepository.findById(match.getHomeTeamId()).orElse(new Team());
        Team awayTeam = teamRepository.findById(match.getAwayTeamId()).orElse(new Team());
        MatchEnricher.enrich(dto, homeTeam, awayTeam);
        return dto;
    }

    @Caching(evict = {
            @CacheEvict(value = "matches", allEntries = true),
            @CacheEvict(value = "fixtures", allEntries = true)
    })
    public void create(CreateMatchDto dto) {
        matchRepository.save(matchMapper.toEntity(dto));
    }

    @Caching(evict = {
            @CacheEvict(value = "matches", allEntries = true),
            @CacheEvict(value = "fixtures", allEntries = true)
    })
    public void update(UpdateMatchDto dto) {
        Match existing = matchRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + dto.getId()));
        Match updated = matchMapper.toEntity(dto);
        updated.setCreatedDate(existing.getCreatedDate());
        matchRepository.save(updated);
    }

    @Caching(evict = {
            @CacheEvict(value = "matches", allEntries = true),
            @CacheEvict(value = "fixtures", allEntries = true)
    })
    public void delete(String id) {
        matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + id));
        matchRepository.deleteById(id);
    }

    @Cacheable(value = "matches", key = "'live'")
    public List<ResultMatchDto> getLiveMatches() {
        return toEnrichedList(matchRepository.findByStatus(MatchStatus.LIVE));
    }

    @Cacheable(value = "matches", key = "'finished'")
    public List<ResultMatchDto> getFinishedMatches() {
        return toEnrichedList(matchRepository.findByStatus(MatchStatus.FINISHED));
    }

    @Cacheable(value = "matches", key = "'upcoming'")
    public List<ResultMatchDto> getUpcomingMatches() {
        return toEnrichedList(matchRepository.findByStatusOrderByMatchDateAsc(MatchStatus.UPCOMING));
    }

    @Cacheable(value = "matches", key = "'featured'")
    public List<ResultMatchDto> getFeaturedMatches() {
        return toEnrichedList(matchRepository.findByIsFeaturedTrue());
    }

    @Cacheable(value = "matches", key = "'detail_' + #id")
    public ResultMatchDetailDto getMatchDetail(String id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found: " + id));
        ResultMatchDetailDto dto = matchMapper.toDetailDto(match);
        Team homeTeam = teamRepository.findById(match.getHomeTeamId()).orElse(new Team());
        Team awayTeam = teamRepository.findById(match.getAwayTeamId()).orElse(new Team());
        MatchEnricher.enrich(dto, homeTeam, awayTeam);
        return dto;
    }

    public long countByStatus(MatchStatus status) {
        return matchRepository.findByStatus(status).size();
    }
}
