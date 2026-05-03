package tr.com.huseyinaydin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tr.com.huseyinaydin.dto.standing.CreateStandingDto;
import tr.com.huseyinaydin.dto.standing.GetStandingByIdDto;
import tr.com.huseyinaydin.dto.standing.ResultStandingDto;
import tr.com.huseyinaydin.dto.standing.UpdateStandingDto;
import tr.com.huseyinaydin.entity.Standing;

@Mapper(componentModel = "spring")
public interface StandingMapper {

    Standing toEntity(CreateStandingDto dto);

    Standing toEntity(UpdateStandingDto dto);

    @Mapping(target = "teamName", ignore = true)
    @Mapping(target = "shortName", ignore = true)
    @Mapping(target = "logoUrl", ignore = true)
    ResultStandingDto toResultDto(Standing entity);

    @Mapping(target = "teamName", ignore = true)
    @Mapping(target = "teamShortName", ignore = true)
    @Mapping(target = "teamLogoUrl", ignore = true)
    GetStandingByIdDto toGetByIdDto(Standing entity);
}
