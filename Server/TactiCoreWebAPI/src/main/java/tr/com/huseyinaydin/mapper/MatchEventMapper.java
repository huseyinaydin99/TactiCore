package tr.com.huseyinaydin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tr.com.huseyinaydin.dto.matchevent.CreateMatchEventDto;
import tr.com.huseyinaydin.dto.matchevent.GetByIdMatchEventDto;
import tr.com.huseyinaydin.dto.matchevent.ResultMatchEventDto;
import tr.com.huseyinaydin.dto.matchevent.UpdateMatchEventDto;
import tr.com.huseyinaydin.entity.MatchEvent;

@Mapper(componentModel = "spring")
public interface MatchEventMapper {

    MatchEvent toEntity(CreateMatchEventDto dto);

    MatchEvent toEntity(UpdateMatchEventDto dto);

    @Mapping(target = "teamName", ignore = true)
    @Mapping(target = "teamLogoUrl", ignore = true)
    @Mapping(target = "playerName", ignore = true)
    @Mapping(target = "playerImageUrl", ignore = true)
    ResultMatchEventDto toResultDto(MatchEvent entity);

    @Mapping(target = "teamName", ignore = true)
    @Mapping(target = "teamLogoUrl", ignore = true)
    @Mapping(target = "playerName", ignore = true)
    @Mapping(target = "playerImageUrl", ignore = true)
    GetByIdMatchEventDto toGetByIdDto(MatchEvent entity);
}
