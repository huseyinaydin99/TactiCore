package tr.com.huseyinaydin.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import tr.com.huseyinaydin.common.ApiResponse;

import java.util.List;

public abstract class AbstractApiService<T> {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String baseUrl;

    protected AbstractApiService(RestTemplate restTemplate,
                                  ObjectMapper objectMapper,
                                  String baseUrl) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.baseUrl = baseUrl;
    }

    protected <R> R getForObject(String url, Class<R> responseType) {
        try {
            String json = restTemplate.getForObject(url, String.class);
            JavaType type = objectMapper.getTypeFactory()
                    .constructParametricType(ApiResponse.class, responseType);
            ApiResponse<R> response = objectMapper.readValue(json, type);
            return response.getData();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Deserializasyon hatası: " + url, e);
        }
    }

    // typeRef örneği: new ParameterizedTypeReference<List<ResultTeamDto>>() {}
    protected <R> List<R> getForList(String url, ParameterizedTypeReference<List<R>> typeRef) {
        try {
            String json = restTemplate.getForObject(url, String.class);
            JavaType listType = objectMapper.getTypeFactory()
                    .constructType(typeRef.getType());
            JavaType apiResponseType = objectMapper.getTypeFactory()
                    .constructParametricType(ApiResponse.class, listType);
            ApiResponse<List<R>> response = objectMapper.readValue(json, apiResponseType);
            return response.getData();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Deserializasyon hatası: " + url, e);
        }
    }

    protected <R> R postForObject(String url, Object request, Class<R> responseType) {
        try {
            String json = restTemplate.postForObject(url, request, String.class);
            if (json == null) return null;
            JavaType type = objectMapper.getTypeFactory()
                    .constructParametricType(ApiResponse.class, responseType);
            ApiResponse<R> response = objectMapper.readValue(json, type);
            return response.getData();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Deserializasyon hatası: " + url, e);
        }
    }

    protected void put(String url, Object request) {
        restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request), Void.class);
    }

    protected void delete(String url) {
        restTemplate.delete(url);
    }

    protected String buildUrl(String endpoint, Object... params) {
        if (params.length == 0) {
            return baseUrl + endpoint;
        }
        return baseUrl + String.format(endpoint, params);
    }
}
