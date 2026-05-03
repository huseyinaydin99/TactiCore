package tr.com.huseyinaydin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tr.com.huseyinaydin.dto.match.CreateMatchDto;
import tr.com.huseyinaydin.dto.match.GetMatchByIdDto;
import tr.com.huseyinaydin.dto.match.ResultMatchDetailDto;
import tr.com.huseyinaydin.dto.match.ResultMatchDto;
import tr.com.huseyinaydin.dto.match.UpdateMatchDto;
import tr.com.huseyinaydin.entity.Match;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    Match toEntity(CreateMatchDto dto);

    Match toEntity(UpdateMatchDto dto);

    @Mapping(target = "homeTeamName", ignore = true)
    @Mapping(target = "homeTeamLogoUrl", ignore = true)
    @Mapping(target = "awayTeamName", ignore = true)
    @Mapping(target = "awayTeamLogoUrl", ignore = true)
    ResultMatchDto toResultDto(Match entity);

    @Mapping(target = "homeTeamName", ignore = true)
    @Mapping(target = "homeTeamLogoUrl", ignore = true)
    @Mapping(target = "awayTeamName", ignore = true)
    @Mapping(target = "awayTeamLogoUrl", ignore = true)
    GetMatchByIdDto toGetByIdDto(Match entity);

    @Mapping(target = "homeTeamName", ignore = true)
    @Mapping(target = "homeTeamShortName", ignore = true)
    @Mapping(target = "homeTeamLogoUrl", ignore = true)
    @Mapping(target = "awayTeamName", ignore = true)
    @Mapping(target = "awayTeamShortName", ignore = true)
    @Mapping(target = "awayTeamLogoUrl", ignore = true)
    ResultMatchDetailDto toDetailDto(Match entity);
}
