package tr.com.huseyinaydin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.dto.team.CreateTeamDto;
import tr.com.huseyinaydin.dto.team.GetTeamByIdDto;
import tr.com.huseyinaydin.dto.team.ResultTeamDto;
import tr.com.huseyinaydin.dto.team.UpdateTeamDto;
import tr.com.huseyinaydin.entity.Team;
import tr.com.huseyinaydin.exception.ResourceNotFoundException;
import tr.com.huseyinaydin.mapper.TeamMapper;
import tr.com.huseyinaydin.repository.TeamRepository;
import tr.com.huseyinaydin.service.TeamService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    @Cacheable(value = "teams", key = "'all'")
    public List<ResultTeamDto> getAll() {
        return teamRepository.findAll().stream()
                .map(teamMapper::toResultDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "teams", key = "#id")
    public GetTeamByIdDto getById(String id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found: " + id));
        return teamMapper.toGetByIdDto(team);
    }

    @CacheEvict(value = "teams", allEntries = true)
    public void create(CreateTeamDto dto) {
        teamRepository.save(teamMapper.toEntity(dto));
    }

    @CacheEvict(value = "teams", allEntries = true)
    public void update(UpdateTeamDto dto) {
        Team existing = teamRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Team not found: " + dto.getId()));
        Team updated = teamMapper.toEntity(dto);
        updated.setCreatedDate(existing.getCreatedDate());
        teamRepository.save(updated);
    }

    @CacheEvict(value = "teams", allEntries = true)
    public void delete(String id) {
        teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found: " + id));
        teamRepository.deleteById(id);
    }
}
