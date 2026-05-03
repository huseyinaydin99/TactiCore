package tr.com.huseyinaydin.service;

import tr.com.huseyinaydin.dto.match.CreateMatchDto;
import tr.com.huseyinaydin.dto.match.GetMatchByIdDto;
import tr.com.huseyinaydin.dto.match.ResultMatchDetailDto;
import tr.com.huseyinaydin.dto.match.ResultMatchDto;
import tr.com.huseyinaydin.dto.match.UpdateMatchDto;
import tr.com.huseyinaydin.entity.enums.MatchStatus;

import java.util.List;

public interface MatchService {

    List<ResultMatchDto> getAll();

    GetMatchByIdDto getById(String id);

    void create(CreateMatchDto dto);

    void update(UpdateMatchDto dto);

    void delete(String id);

    List<ResultMatchDto> getLiveMatches();

    List<ResultMatchDto> getFinishedMatches();

    List<ResultMatchDto> getUpcomingMatches();

    List<ResultMatchDto> getFeaturedMatches();

    ResultMatchDetailDto getMatchDetail(String id);

    long countByStatus(MatchStatus status);
}
