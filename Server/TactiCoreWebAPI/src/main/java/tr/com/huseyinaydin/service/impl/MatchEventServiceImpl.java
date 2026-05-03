package tr.com.huseyinaydin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.dto.matchevent.CreateMatchEventDto;
import tr.com.huseyinaydin.dto.matchevent.DashboardSummaryDto;
import tr.com.huseyinaydin.dto.matchevent.GetByIdMatchEventDto;
import tr.com.huseyinaydin.dto.matchevent.ResultMatchEventDto;
import tr.com.huseyinaydin.dto.matchevent.UpdateMatchEventDto;
import tr.com.huseyinaydin.entity.MatchEvent;
import tr.com.huseyinaydin.entity.Player;
import tr.com.huseyinaydin.entity.Team;
import tr.com.huseyinaydin.exception.ResourceNotFoundException;
import tr.com.huseyinaydin.mapper.MatchEventMapper;
import tr.com.huseyinaydin.repository.MatchEventRepository;
import tr.com.huseyinaydin.repository.PlayerRepository;
import tr.com.huseyinaydin.repository.TeamRepository;
import tr.com.huseyinaydin.service.MatchEventService;
import tr.com.huseyinaydin.service.business.DashboardSummaryBuilder;
import tr.com.huseyinaydin.service.business.MatchEventEnricher;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatchEventServiceImpl implements MatchEventService {

    private final MatchEventRepository matchEventRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final MatchEventMapper matchEventMapper;

    private ResultMatchEventDto toEnrichedDto(MatchEvent event) {
        ResultMatchEventDto dto = matchEventMapper.toResultDto(event);
        Team team = teamRepository.findById(event.getTeamId()).orElse(new Team());
        Player player = event.getPlayerId() != null
                ? playerRepository.findById(event.getPlayerId()).orElse(null)
                : null;
        MatchEventEnricher.enrich(dto, team, player);
        return dto;
    }

    @Cacheable(value = "matchEvents", key = "'all'")
    public List<ResultMatchEventDto> getAll() {
        return matchEventRepository.findAll().stream()
                .map(this::toEnrichedDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "matchEvents", key = "#id")
    public GetByIdMatchEventDto getById(String id) {
        MatchEvent event = matchEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MatchEvent not found: " + id));
        GetByIdMatchEventDto dto = matchEventMapper.toGetByIdDto(event);
        Team team = teamRepository.findById(event.getTeamId()).orElse(new Team());
        Player player = event.getPlayerId() != null
                ? playerRepository.findById(event.getPlayerId()).orElse(null)
                : null;
        MatchEventEnricher.enrich(dto, team, player);
        return dto;
    }

    @CacheEvict(value = "matchEvents", allEntries = true)
    public void create(CreateMatchEventDto dto) {
        matchEventRepository.save(matchEventMapper.toEntity(dto));
    }

    @CacheEvict(value = "matchEvents", allEntries = true)
    public void update(UpdateMatchEventDto dto) {
        MatchEvent existing = matchEventRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("MatchEvent not found: " + dto.getId()));
        MatchEvent updated = matchEventMapper.toEntity(dto);
        updated.setCreatedDate(existing.getCreatedDate());
        matchEventRepository.save(updated);
    }

    @CacheEvict(value = "matchEvents", allEntries = true)
    public void delete(String id) {
        matchEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MatchEvent not found: " + id));
        matchEventRepository.deleteById(id);
    }

    @Cacheable(value = "matchEvents", key = "'match_' + #matchId")
    public List<ResultMatchEventDto> getByMatchId(String matchId) {
        return matchEventRepository.findByMatchIdOrderByMinuteAsc(matchId).stream()
                .map(this::toEnrichedDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "matchEvents", key = "'dashboard'")
    public DashboardSummaryDto getDashboardSummary() {
        return DashboardSummaryBuilder.build(matchEventRepository.findAll());
    }
}
