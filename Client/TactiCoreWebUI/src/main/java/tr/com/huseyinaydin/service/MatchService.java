package tr.com.huseyinaydin.service;

import tr.com.huseyinaydin.dto.match.CreateMatchDto;
import tr.com.huseyinaydin.dto.match.GetMatchByIdDto;
import tr.com.huseyinaydin.dto.match.ResultMatchDetailDto;
import tr.com.huseyinaydin.dto.match.ResultMatchDto;
import tr.com.huseyinaydin.dto.match.UpdateMatchDto;

import java.util.List;

public interface MatchService {

    List<ResultMatchDto> getAll();
    GetMatchByIdDto getById(String id);
    ResultMatchDetailDto getDetail(String id);
    List<ResultMatchDto> getLive();
    List<ResultMatchDto> getFinished();
    List<ResultMatchDto> getUpcoming();
    List<ResultMatchDto> getFeatured();
    long getLiveCount();
    long getFinishedCount();
    long getUpcomingCount();
    void create(CreateMatchDto dto);
    void update(UpdateMatchDto dto);
    void delete(String id);
}
