package tr.com.huseyinaydin.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tr.com.huseyinaydin.constant.ApiConstants;
import tr.com.huseyinaydin.dto.match.CreateMatchDto;
import tr.com.huseyinaydin.dto.match.GetMatchByIdDto;
import tr.com.huseyinaydin.dto.match.ResultMatchDetailDto;
import tr.com.huseyinaydin.dto.match.ResultMatchDto;
import tr.com.huseyinaydin.dto.match.UpdateMatchDto;
import tr.com.huseyinaydin.service.AbstractApiService;
import tr.com.huseyinaydin.service.MatchService;
import tr.com.huseyinaydin.service.MatchStatusObserver;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchServiceImpl extends AbstractApiService<ResultMatchDto> implements MatchService {

    private final List<MatchStatusObserver> observers = new ArrayList<>();

    public MatchServiceImpl(RestTemplate restTemplate,
                            ObjectMapper objectMapper,
                            @Value("${api.base-url}") String baseUrl) {
        super(restTemplate, objectMapper, baseUrl);
    }

    public void registerObserver(MatchStatusObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(MatchStatusObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers(ResultMatchDto match) {
        observers.forEach(o -> o.onMatchStatusChange(match));
    }

    @Override
    public List<ResultMatchDto> getAll() {
        return getForList(buildUrl(ApiConstants.MATCH_BASE),
                new ParameterizedTypeReference<>() {});
    }

    @Override
    public GetMatchByIdDto getById(String id) {
        return getForObject(buildUrl(ApiConstants.MATCH_GET_BY_ID + "?id=%s", id),
                GetMatchByIdDto.class);
    }

    @Override
    public ResultMatchDetailDto getDetail(String id) {
        return getForObject(buildUrl(ApiConstants.MATCH_DETAIL + "?id=%s", id),
                ResultMatchDetailDto.class);
    }

    @Override
    public List<ResultMatchDto> getLive() {
        List<ResultMatchDto> live = getForList(buildUrl(ApiConstants.MATCH_LIVE),
                new ParameterizedTypeReference<>() {});
        if (live != null && !live.isEmpty()) {
            live.forEach(this::notifyObservers);
        }
        return live;
    }

    @Override
    public List<ResultMatchDto> getFinished() {
        return getForList(buildUrl(ApiConstants.MATCH_FINISHED),
                new ParameterizedTypeReference<>() {});
    }

    @Override
    public List<ResultMatchDto> getUpcoming() {
        return getForList(buildUrl(ApiConstants.MATCH_UPCOMING),
                new ParameterizedTypeReference<>() {});
    }

    @Override
    public List<ResultMatchDto> getFeatured() {
        return getForList(buildUrl(ApiConstants.MATCH_FEATURED),
                new ParameterizedTypeReference<>() {});
    }

    @Override
    public long getLiveCount() {
        Long count = getForObject(buildUrl(ApiConstants.MATCH_LIVE_COUNT), Long.class);
        return count != null ? count : 0L;
    }

    @Override
    public long getFinishedCount() {
        Long count = getForObject(buildUrl(ApiConstants.MATCH_FINISHED_COUNT), Long.class);
        return count != null ? count : 0L;
    }

    @Override
    public long getUpcomingCount() {
        Long count = getForObject(buildUrl(ApiConstants.MATCH_UPCOMING_COUNT), Long.class);
        return count != null ? count : 0L;
    }

    @Override
    public void create(CreateMatchDto dto) {
        postForObject(buildUrl(ApiConstants.MATCH_BASE), dto, Void.class);
    }

    @Override
    public void update(UpdateMatchDto dto) {
        put(buildUrl(ApiConstants.MATCH_BASE), dto);
    }

    @Override
    public void delete(String id) {
        delete(buildUrl(ApiConstants.MATCH_BASE + "?id=%s", id));
    }
}
