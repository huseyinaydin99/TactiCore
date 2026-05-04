package tr.com.huseyinaydin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tr.com.huseyinaydin.constant.ApiConstants;
import tr.com.huseyinaydin.dto.matchevent.CreateMatchEventDto;
import tr.com.huseyinaydin.dto.matchevent.DashboardSummaryDto;
import tr.com.huseyinaydin.dto.matchevent.GetByIdMatchEventDto;
import tr.com.huseyinaydin.dto.matchevent.ResultMatchEventDto;
import tr.com.huseyinaydin.dto.matchevent.UpdateMatchEventDto;
import tr.com.huseyinaydin.service.AbstractApiService;
import tr.com.huseyinaydin.service.MatchEventService;

import java.util.List;

@Service
public class MatchEventServiceImpl extends AbstractApiService<ResultMatchEventDto> implements MatchEventService {

    public MatchEventServiceImpl(RestTemplate restTemplate,
                                 ObjectMapper objectMapper,
                                 @Value("${api.base-url}") String baseUrl) {
        super(restTemplate, objectMapper, baseUrl);
    }

    @Override
    public List<ResultMatchEventDto> getAll() {
        return getForList(buildUrl(ApiConstants.MATCH_EVENT_BASE),
                new ParameterizedTypeReference<>() {});
    }

    @Override
    public GetByIdMatchEventDto getById(String id) {
        return getForObject(buildUrl(ApiConstants.MATCH_EVENT_GET_BY_ID + "?id=%s", id),
                GetByIdMatchEventDto.class);
    }

    @Override
    public List<ResultMatchEventDto> getByMatchId(String id) {
        return getForList(buildUrl(ApiConstants.MATCH_EVENT_BY_MATCH + "?id=%s", id),
                new ParameterizedTypeReference<>() {});
    }

    @Override
    public DashboardSummaryDto getDashboardSummary() {
        return getForObject(buildUrl(ApiConstants.DASHBOARD_SUMMARY),
                DashboardSummaryDto.class);
    }

    @Override
    public void create(CreateMatchEventDto dto) {
        postForObject(buildUrl(ApiConstants.MATCH_EVENT_BASE), dto, Void.class);
    }

    @Override
    public void update(UpdateMatchEventDto dto) {
        put(buildUrl(ApiConstants.MATCH_EVENT_BASE), dto);
    }

    @Override
    public void delete(String id) {
        delete(buildUrl(ApiConstants.MATCH_EVENT_BASE + "?id=%s", id));
    }
}
