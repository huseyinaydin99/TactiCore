package tr.com.huseyinaydin.service;

import tr.com.huseyinaydin.dto.news.CreateNewsDto;
import tr.com.huseyinaydin.dto.news.ResultNewsDto;
import tr.com.huseyinaydin.dto.news.UpdateNewsDto;

import java.util.List;

public interface NewsService {

    List<ResultNewsDto> getAll();

    ResultNewsDto getById(String id);

    void create(CreateNewsDto dto);

    void update(UpdateNewsDto dto);

    void delete(String id);

    List<ResultNewsDto> getMainNews();

    List<ResultNewsDto> getActiveNews();
}
