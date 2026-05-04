package tr.com.huseyinaydin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tr.com.huseyinaydin.constant.ApiConstants;
import tr.com.huseyinaydin.dto.team.CreateTeamDto;
import tr.com.huseyinaydin.dto.team.GetTeamByIdDto;
import tr.com.huseyinaydin.dto.team.ResultTeamDto;
import tr.com.huseyinaydin.dto.team.UpdateTeamDto;
import tr.com.huseyinaydin.service.AbstractApiService;
import tr.com.huseyinaydin.service.TeamService;

import java.util.List;

@Service
public class TeamServiceImpl extends AbstractApiService<ResultTeamDto> implements TeamService {

    public TeamServiceImpl(RestTemplate restTemplate,
                           ObjectMapper objectMapper,
                           @Value("${api.base-url}") String baseUrl) {
        super(restTemplate, objectMapper, baseUrl);
    }

    @Override
    public List<ResultTeamDto> getAll() {
        return getForList(buildUrl(ApiConstants.TEAM_BASE),
                new ParameterizedTypeReference<>() {});
    }

    @Override
    public GetTeamByIdDto getById(String id) {
        return getForObject(buildUrl(ApiConstants.TEAM_GET_BY_ID + "?id=%s", id),
                GetTeamByIdDto.class);
    }

    @Override
    public void create(CreateTeamDto dto) {
        postForObject(buildUrl(ApiConstants.TEAM_BASE), dto, Void.class);
    }

    @Override
    public void update(UpdateTeamDto dto) {
        put(buildUrl(ApiConstants.TEAM_BASE), dto);
    }

    @Override
    public void delete(String id) {
        delete(buildUrl(ApiConstants.TEAM_BASE + "?id=%s", id));
    }
}
