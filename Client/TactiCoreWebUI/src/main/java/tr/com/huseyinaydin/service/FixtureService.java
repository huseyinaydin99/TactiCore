package tr.com.huseyinaydin.service;

import tr.com.huseyinaydin.dto.fixture.ResultFeaturedFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultLiveFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultWeekSummaryDto;

import java.util.List;

public interface FixtureService {

    List<ResultFixtureDto> getAll();
    List<ResultFixtureDto> getByWeek(int week);
    List<ResultLiveFixtureDto> getLive();
    List<ResultFeaturedFixtureDto> getFeatured();
    ResultWeekSummaryDto getWeekSummary(int week);
}
