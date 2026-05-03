package tr.com.huseyinaydin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tr.com.huseyinaydin.common.ApiResponse;
import tr.com.huseyinaydin.dto.match.CreateMatchDto;
import tr.com.huseyinaydin.dto.match.GetMatchByIdDto;
import tr.com.huseyinaydin.dto.match.ResultMatchDetailDto;
import tr.com.huseyinaydin.dto.match.ResultMatchDto;
import tr.com.huseyinaydin.dto.match.UpdateMatchDto;
import tr.com.huseyinaydin.entity.enums.MatchStatus;
import tr.com.huseyinaydin.service.MatchService;

import java.util.List;

@RestController
@RequestMapping("/match")
@Validated
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResultMatchDto>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(matchService.getAll()));
    }

    @GetMapping("/GetMatch")
    public ResponseEntity<ApiResponse<GetMatchByIdDto>> getById(@RequestParam String id) {
        return ResponseEntity.ok(ApiResponse.success(matchService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody CreateMatchDto dto) {
        matchService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Match created"));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Void>> update(@Valid @RequestBody UpdateMatchDto dto) {
        matchService.update(dto);
        return ResponseEntity.ok(ApiResponse.success("Match updated"));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(@RequestParam String id) {
        matchService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Match deleted"));
    }

    @GetMapping("/LiveMatches")
    public ResponseEntity<ApiResponse<List<ResultMatchDto>>> getLiveMatches() {
        return ResponseEntity.ok(ApiResponse.success(matchService.getLiveMatches()));
    }

    @GetMapping("/FinishedMatches")
    public ResponseEntity<ApiResponse<List<ResultMatchDto>>> getFinishedMatches() {
        return ResponseEntity.ok(ApiResponse.success(matchService.getFinishedMatches()));
    }

    @GetMapping("/UpcomingMatches")
    public ResponseEntity<ApiResponse<List<ResultMatchDto>>> getUpcomingMatches() {
        return ResponseEntity.ok(ApiResponse.success(matchService.getUpcomingMatches()));
    }

    @GetMapping("/FeaturedMatch")
    public ResponseEntity<ApiResponse<List<ResultMatchDto>>> getFeaturedMatches() {
        return ResponseEntity.ok(ApiResponse.success(matchService.getFeaturedMatches()));
    }

    @GetMapping("/GetMatchDetail")
    public ResponseEntity<ApiResponse<ResultMatchDetailDto>> getMatchDetail(@RequestParam String id) {
        return ResponseEntity.ok(ApiResponse.success(matchService.getMatchDetail(id)));
    }

    @GetMapping("/LiveCount")
    public ResponseEntity<ApiResponse<Long>> getLiveCount() {
        return ResponseEntity.ok(ApiResponse.success(matchService.countByStatus(MatchStatus.LIVE)));
    }

    @GetMapping("/FinishedCount")
    public ResponseEntity<ApiResponse<Long>> getFinishedCount() {
        return ResponseEntity.ok(ApiResponse.success(matchService.countByStatus(MatchStatus.FINISHED)));
    }

    @GetMapping("/UpcomingCount")
    public ResponseEntity<ApiResponse<Long>> getUpcomingCount() {
        return ResponseEntity.ok(ApiResponse.success(matchService.countByStatus(MatchStatus.UPCOMING)));
    }
}
