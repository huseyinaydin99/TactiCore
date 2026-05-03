package tr.com.huseyinaydin.service;

import tr.com.huseyinaydin.dto.team.CreateTeamDto;
import tr.com.huseyinaydin.dto.team.GetTeamByIdDto;
import tr.com.huseyinaydin.dto.team.ResultTeamDto;
import tr.com.huseyinaydin.dto.team.UpdateTeamDto;

import java.util.List;

public interface TeamService {

    List<ResultTeamDto> getAll();

    GetTeamByIdDto getById(String id);

    void create(CreateTeamDto dto);

    void update(UpdateTeamDto dto);

    void delete(String id);
}
