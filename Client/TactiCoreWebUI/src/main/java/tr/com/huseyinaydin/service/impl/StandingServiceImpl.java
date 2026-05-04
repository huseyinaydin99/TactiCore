package tr.com.huseyinaydin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tr.com.huseyinaydin.constant.ApiConstants;
import tr.com.huseyinaydin.dto.standing.CreateStandingDto;
import tr.com.huseyinaydin.dto.standing.GetStandingByIdDto;
import tr.com.huseyinaydin.dto.standing.ResultStandingDto;
import tr.com.huseyinaydin.dto.standing.UpdateStandingDto;
import tr.com.huseyinaydin.service.AbstractApiService;
import tr.com.huseyinaydin.service.StandingService;

import java.util.List;

@Service
public class StandingServiceImpl extends AbstractApiService<ResultStandingDto> implements StandingService {

    public StandingServiceImpl(RestTemplate restTemplate,
                               ObjectMapper objectMapper,
                               @Value("${api.base-url}") String baseUrl) {
        super(restTemplate, objectMapper, baseUrl);
    }

    @Override
    public List<ResultStandingDto> getAll() {
        return getForList(buildUrl(ApiConstants.STANDING_BASE),
                new ParameterizedTypeReference<>() {});
    }

    @Override
    public GetStandingByIdDto getById(String id) {
        return getForObject(buildUrl(ApiConstants.STANDING_GET_BY_ID + "?id=%s", id),
                GetStandingByIdDto.class);
    }

    @Override
    public void create(CreateStandingDto dto) {
        postForObject(buildUrl(ApiConstants.STANDING_BASE), dto, Void.class);
    }

    @Override
    public void update(UpdateStandingDto dto) {
        put(buildUrl(ApiConstants.STANDING_BASE), dto);
    }

    @Override
    public void delete(String id) {
        delete(buildUrl(ApiConstants.STANDING_BASE + "?id=%s", id));
    }
}
