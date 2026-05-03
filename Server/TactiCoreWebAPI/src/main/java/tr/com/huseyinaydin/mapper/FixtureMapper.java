package tr.com.huseyinaydin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tr.com.huseyinaydin.dto.fixture.ResultFeaturedFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultLiveFixtureDto;
import tr.com.huseyinaydin.entity.Match;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FixtureMapper {

    @Mapping(target = "homeTeamName", ignore = true)
    @Mapping(target = "homeTeamLogoUrl", ignore = true)
    @Mapping(target = "awayTeamName", ignore = true)
    @Mapping(target = "awayTeamLogoUrl", ignore = true)
    ResultFixtureDto toFixtureDto(Match entity);

    List<ResultFixtureDto> toFixtureDtoList(List<Match> entities);

    @Mapping(target = "homeTeamName", ignore = true)
    @Mapping(target = "homeTeamLogoUrl", ignore = true)
    @Mapping(target = "awayTeamName", ignore = true)
    @Mapping(target = "awayTeamLogoUrl", ignore = true)
    ResultLiveFixtureDto toLiveFixtureDto(Match entity);

    List<ResultLiveFixtureDto> toLiveFixtureDtoList(List<Match> entities);

    @Mapping(target = "homeTeamName", ignore = true)
    @Mapping(target = "homeTeamLogoUrl", ignore = true)
    @Mapping(target = "awayTeamName", ignore = true)
    @Mapping(target = "awayTeamLogoUrl", ignore = true)
    ResultFeaturedFixtureDto toFeaturedFixtureDto(Match entity);

    List<ResultFeaturedFixtureDto> toFeaturedFixtureDtoList(List<Match> entities);
}
