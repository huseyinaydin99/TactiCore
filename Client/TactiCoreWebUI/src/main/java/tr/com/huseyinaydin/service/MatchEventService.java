package tr.com.huseyinaydin.service;

import tr.com.huseyinaydin.dto.matchevent.CreateMatchEventDto;
import tr.com.huseyinaydin.dto.matchevent.DashboardSummaryDto;
import tr.com.huseyinaydin.dto.matchevent.GetByIdMatchEventDto;
import tr.com.huseyinaydin.dto.matchevent.ResultMatchEventDto;
import tr.com.huseyinaydin.dto.matchevent.UpdateMatchEventDto;

import java.util.List;

public interface MatchEventService {

    List<ResultMatchEventDto> getAll();
    GetByIdMatchEventDto getById(String id);
    List<ResultMatchEventDto> getByMatchId(String id);
    DashboardSummaryDto getDashboardSummary();
    void create(CreateMatchEventDto dto);
    void update(UpdateMatchEventDto dto);
    void delete(String id);
}
