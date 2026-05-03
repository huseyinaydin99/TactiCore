package tr.com.huseyinaydin.service;

import tr.com.huseyinaydin.dto.standing.CreateStandingDto;
import tr.com.huseyinaydin.dto.standing.GetStandingByIdDto;
import tr.com.huseyinaydin.dto.standing.ResultStandingDto;
import tr.com.huseyinaydin.dto.standing.UpdateStandingDto;

import java.util.List;

public interface StandingService {

    List<ResultStandingDto> getAll();

    GetStandingByIdDto getById(String id);

    void create(CreateStandingDto dto);

    void update(UpdateStandingDto dto);

    void delete(String id);
}
