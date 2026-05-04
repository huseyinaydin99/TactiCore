package tr.com.huseyinaydin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tr.com.huseyinaydin.constant.ApiConstants;
import tr.com.huseyinaydin.dto.player.ResultPlayerDto;
import tr.com.huseyinaydin.service.AbstractApiService;
import tr.com.huseyinaydin.service.PlayerService;

import java.util.List;

@Service
public class PlayerServiceImpl extends AbstractApiService<ResultPlayerDto> implements PlayerService {

    public PlayerServiceImpl(RestTemplate restTemplate,
                             ObjectMapper objectMapper,
                             @Value("${api.base-url}") String baseUrl) {
        super(restTemplate, objectMapper, baseUrl);
    }

    @Override
    public List<ResultPlayerDto> getAll() {
        return getForList(buildUrl(ApiConstants.PLAYER_BASE),
                new ParameterizedTypeReference<>() {});
    }
}
