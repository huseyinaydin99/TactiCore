package tr.com.huseyinaydin.mapper;

import org.mapstruct.Mapper;
import tr.com.huseyinaydin.dto.team.CreateTeamDto;
import tr.com.huseyinaydin.dto.team.GetTeamByIdDto;
import tr.com.huseyinaydin.dto.team.ResultTeamDto;
import tr.com.huseyinaydin.dto.team.UpdateTeamDto;
import tr.com.huseyinaydin.entity.Team;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    Team toEntity(CreateTeamDto dto);

    Team toEntity(UpdateTeamDto dto);

    ResultTeamDto toResultDto(Team entity);

    GetTeamByIdDto toGetByIdDto(Team entity);
}
