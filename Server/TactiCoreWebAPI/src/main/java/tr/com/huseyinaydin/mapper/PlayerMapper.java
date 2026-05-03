package tr.com.huseyinaydin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tr.com.huseyinaydin.dto.player.ResultPlayerDto;
import tr.com.huseyinaydin.entity.Player;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Mapping(target = "teamName", ignore = true)
    @Mapping(target = "teamLogoUrl", ignore = true)
    ResultPlayerDto toResultDto(Player entity);
}
