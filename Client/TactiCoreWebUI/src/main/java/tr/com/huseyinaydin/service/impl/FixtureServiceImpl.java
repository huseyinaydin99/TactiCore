package tr.com.huseyinaydin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tr.com.huseyinaydin.constant.ApiConstants;
import tr.com.huseyinaydin.dto.fixture.ResultFeaturedFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultLiveFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultWeekSummaryDto;
import tr.com.huseyinaydin.service.AbstractApiService;
import tr.com.huseyinaydin.service.FixtureService;

import java.util.List;

@Service
public class FixtureServiceImpl extends AbstractApiService<ResultFixtureDto> implements FixtureService {

    public FixtureServiceImpl(RestTemplate restTemplate,
                              ObjectMapper objectMapper,
                              @Value("${api.base-url}") String baseUrl) {
        super(restTemplate, objectMapper, baseUrl);
    }

    @Override
    public List<ResultFixtureDto> getAll() {
        return getForList(buildUrl(ApiConstants.FIXTURE_BASE),
                new ParameterizedTypeReference<>() {});
    }

    @Override
    public List<ResultFixtureDto> getByWeek(int week) {
        return getForList(buildUrl(ApiConstants.FIXTURE_BY_WEEK + "?week=%d", week),
                new ParameterizedTypeReference<>() {});
    }

    @Override
    public List<ResultLiveFixtureDto> getLive() {
        return getForList(buildUrl(ApiConstants.FIXTURE_LIVE),
                new ParameterizedTypeReference<>() {});
    }

    @Override
    public List<ResultFeaturedFixtureDto> getFeatured() {
        return getForList(buildUrl(ApiConstants.FIXTURE_FEATURED),
                new ParameterizedTypeReference<>() {});
    }

    @Override
    public ResultWeekSummaryDto getWeekSummary(int week) {
        return getForObject(buildUrl(ApiConstants.FIXTURE_WEEK_SUMMARY + "?week=%d", week),
                ResultWeekSummaryDto.class);
    }
}
