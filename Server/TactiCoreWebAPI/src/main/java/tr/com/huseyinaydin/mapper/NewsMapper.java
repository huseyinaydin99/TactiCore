package tr.com.huseyinaydin.mapper;

import org.mapstruct.Mapper;
import tr.com.huseyinaydin.dto.news.CreateNewsDto;
import tr.com.huseyinaydin.dto.news.ResultNewsDto;
import tr.com.huseyinaydin.dto.news.UpdateNewsDto;
import tr.com.huseyinaydin.entity.News;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    News toEntity(CreateNewsDto dto);

    News toEntity(UpdateNewsDto dto);

    ResultNewsDto toResultDto(News entity);
}
