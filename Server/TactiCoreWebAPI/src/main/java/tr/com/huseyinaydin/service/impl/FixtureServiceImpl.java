package tr.com.huseyinaydin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.dto.fixture.ResultFeaturedFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultLiveFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultWeekSummaryDto;
import tr.com.huseyinaydin.entity.Match;
import tr.com.huseyinaydin.entity.Team;
import tr.com.huseyinaydin.entity.enums.MatchStatus;
import tr.com.huseyinaydin.mapper.FixtureMapper;
import tr.com.huseyinaydin.repository.MatchRepository;
import tr.com.huseyinaydin.repository.TeamRepository;
import tr.com.huseyinaydin.service.FixtureService;
import tr.com.huseyinaydin.service.business.FixtureEnricher;
import tr.com.huseyinaydin.service.business.FixtureSummaryBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FixtureServiceImpl implements FixtureService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final FixtureMapper fixtureMapper;

    private Map<String, Team> loadTeamMap(List<Match> matches) {
        Set<String> ids = new HashSet<>();
        matches.forEach(m -> {
            ids.add(m.getHomeTeamId());
            ids.add(m.getAwayTeamId());
        });
        return teamRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(Team::getId, t -> t));
    }

    private List<ResultFixtureDto> buildFixtureDtos(List<Match> matches, Map<String, Team> teamMap) {
        return matches.stream()
                .map(match -> {
                    ResultFixtureDto dto = fixtureMapper.toFixtureDto(match);
                    FixtureEnricher.enrich(dto,
                            teamMap.getOrDefault(match.getHomeTeamId(), new Team()),
                            teamMap.getOrDefault(match.getAwayTeamId(), new Team()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Cacheable(value = "fixtures", key = "'all'")
    public List<ResultFixtureDto> getAllFixtures() {
        List<Match> matches = matchRepository.findAll(Sort.by("matchDate").ascending());
        return buildFixtureDtos(matches, loadTeamMap(matches));
    }

    @Cacheable(value = "fixtures", key = "#week")
    public List<ResultFixtureDto> getByWeek(int week) {
        List<Match> matches = matchRepository.findByWeek(week);
        return buildFixtureDtos(matches, loadTeamMap(matches));
    }

    @Cacheable(value = "fixtures", key = "'live'")
    public List<ResultLiveFixtureDto> getLiveFixtures() {
        List<Match> matches = matchRepository.findByStatus(MatchStatus.LIVE);
        Map<String, Team> teamMap = loadTeamMap(matches);
        return matches.stream()
                .map(match -> {
                    ResultLiveFixtureDto dto = fixtureMapper.toLiveFixtureDto(match);
                    FixtureEnricher.enrich(dto,
                            teamMap.getOrDefault(match.getHomeTeamId(), new Team()),
                            teamMap.getOrDefault(match.getAwayTeamId(), new Team()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Cacheable(value = "fixtures", key = "'featured'")
    public List<ResultFeaturedFixtureDto> getFeaturedFixtures() {
        List<Match> matches = matchRepository.findByIsFeaturedTrue();
        Map<String, Team> teamMap = loadTeamMap(matches);
        return matches.stream()
                .map(match -> {
                    ResultFeaturedFixtureDto dto = fixtureMapper.toFeaturedFixtureDto(match);
                    FixtureEnricher.enrich(dto,
                            teamMap.getOrDefault(match.getHomeTeamId(), new Team()),
                            teamMap.getOrDefault(match.getAwayTeamId(), new Team()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Cacheable(value = "fixtures", key = "'week_summary_' + #week")
    public ResultWeekSummaryDto getWeekSummary(int week) {
        List<Match> matches = matchRepository.findByWeek(week);
        Map<String, Team> teamMap = loadTeamMap(matches);
        List<ResultFixtureDto> fixtureDtos = buildFixtureDtos(matches, teamMap);
        return FixtureSummaryBuilder.build(week, matches, fixtureDtos);
    }
}
