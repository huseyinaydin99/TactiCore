package tr.com.huseyinaydin.service;

import tr.com.huseyinaydin.dto.player.ResultPlayerDto;

import java.util.List;

public interface PlayerService {

    List<ResultPlayerDto> getAll();

    List<ResultPlayerDto> getByTeamId(String teamId);
}
