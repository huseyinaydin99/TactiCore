package tr.com.huseyinaydin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.dto.player.ResultPlayerDto;
import tr.com.huseyinaydin.entity.Player;
import tr.com.huseyinaydin.entity.Team;
import tr.com.huseyinaydin.exception.ResourceNotFoundException;
import tr.com.huseyinaydin.mapper.PlayerMapper;
import tr.com.huseyinaydin.repository.PlayerRepository;
import tr.com.huseyinaydin.repository.TeamRepository;
import tr.com.huseyinaydin.service.PlayerService;
import tr.com.huseyinaydin.service.business.PlayerEnricher;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final PlayerMapper playerMapper;

    @Cacheable(value = "players", key = "'all'")
    public List<ResultPlayerDto> getAll() {
        List<Player> players = playerRepository.findAll();
        Map<String, Team> teamMap = teamRepository.findAllById(
                players.stream().map(Player::getTeamId).collect(Collectors.toSet())
        ).stream().collect(Collectors.toMap(Team::getId, t -> t));

        return players.stream()
                .map(player -> {
                    ResultPlayerDto dto = playerMapper.toResultDto(player);
                    Team team = teamMap.get(player.getTeamId());
                    if (team != null) PlayerEnricher.enrich(dto, team);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Cacheable(value = "players", key = "#id")
    public ResultPlayerDto getById(String id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found: " + id));
        ResultPlayerDto dto = playerMapper.toResultDto(player);
        teamRepository.findById(player.getTeamId())
                .ifPresent(team -> PlayerEnricher.enrich(dto, team));
        return dto;
    }
}
