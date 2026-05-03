package tr.com.huseyinaydin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.dto.standing.CreateStandingDto;
import tr.com.huseyinaydin.dto.standing.GetStandingByIdDto;
import tr.com.huseyinaydin.dto.standing.ResultStandingDto;
import tr.com.huseyinaydin.dto.standing.UpdateStandingDto;
import tr.com.huseyinaydin.entity.Standing;
import tr.com.huseyinaydin.entity.Team;
import tr.com.huseyinaydin.exception.ResourceNotFoundException;
import tr.com.huseyinaydin.mapper.StandingMapper;
import tr.com.huseyinaydin.repository.StandingRepository;
import tr.com.huseyinaydin.repository.TeamRepository;
import tr.com.huseyinaydin.service.StandingService;
import tr.com.huseyinaydin.service.business.StandingEnricher;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StandingServiceImpl implements StandingService {

    private final StandingRepository standingRepository;
    private final TeamRepository teamRepository;
    private final StandingMapper standingMapper;

    @Cacheable(value = "standings", key = "'all'")
    public List<ResultStandingDto> getAll() {
        List<Standing> standings = standingRepository.findAllByOrderByPositionAsc();
        Map<String, Team> teamMap = teamRepository.findAllById(
                standings.stream().map(Standing::getTeamId).collect(Collectors.toSet())
        ).stream().collect(Collectors.toMap(Team::getId, t -> t));

        return standings.stream()
                .map(standing -> {
                    ResultStandingDto dto = standingMapper.toResultDto(standing);
                    Team team = teamMap.get(standing.getTeamId());
                    if (team != null) StandingEnricher.enrich(dto, team);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Cacheable(value = "standings", key = "#id")
    public GetStandingByIdDto getById(String id) {
        Standing standing = standingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Standing not found: " + id));
        GetStandingByIdDto dto = standingMapper.toGetByIdDto(standing);
        teamRepository.findById(standing.getTeamId())
                .ifPresent(team -> StandingEnricher.enrich(dto, team));
        return dto;
    }

    @CacheEvict(value = "standings", allEntries = true)
    public void create(CreateStandingDto dto) {
        standingRepository.save(standingMapper.toEntity(dto));
    }

    @CacheEvict(value = "standings", allEntries = true)
    public void update(UpdateStandingDto dto) {
        Standing existing = standingRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Standing not found: " + dto.getId()));
        Standing updated = standingMapper.toEntity(dto);
        updated.setCreatedDate(existing.getCreatedDate());
        standingRepository.save(updated);
    }

    @CacheEvict(value = "standings", allEntries = true)
    public void delete(String id) {
        standingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Standing not found: " + id));
        standingRepository.deleteById(id);
    }
}
