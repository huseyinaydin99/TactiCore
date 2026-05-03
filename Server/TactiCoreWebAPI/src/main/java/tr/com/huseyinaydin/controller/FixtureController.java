package tr.com.huseyinaydin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tr.com.huseyinaydin.common.ApiResponse;
import tr.com.huseyinaydin.dto.fixture.ResultFeaturedFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultLiveFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultWeekSummaryDto;
import tr.com.huseyinaydin.service.FixtureService;

import java.util.List;

@RestController
@RequestMapping("/fixture")
@Validated
@RequiredArgsConstructor
public class FixtureController {

    private final FixtureService fixtureService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResultFixtureDto>>> getAllFixtures() {
        return ResponseEntity.ok(ApiResponse.success(fixtureService.getAllFixtures()));
    }

    @GetMapping("/GetByWeek")
    public ResponseEntity<ApiResponse<List<ResultFixtureDto>>> getByWeek(@RequestParam int week) {
        return ResponseEntity.ok(ApiResponse.success(fixtureService.getByWeek(week)));
    }

    @GetMapping("/GetLiveMatches")
    public ResponseEntity<ApiResponse<List<ResultLiveFixtureDto>>> getLiveMatches() {
        return ResponseEntity.ok(ApiResponse.success(fixtureService.getLiveFixtures()));
    }

    @GetMapping("/GetFeaturedMatches")
    public ResponseEntity<ApiResponse<List<ResultFeaturedFixtureDto>>> getFeaturedMatches() {
        return ResponseEntity.ok(ApiResponse.success(fixtureService.getFeaturedFixtures()));
    }

    @GetMapping("/GetWeekSummary")
    public ResponseEntity<ApiResponse<ResultWeekSummaryDto>> getWeekSummary(@RequestParam int week) {
        return ResponseEntity.ok(ApiResponse.success(fixtureService.getWeekSummary(week)));
    }
}
