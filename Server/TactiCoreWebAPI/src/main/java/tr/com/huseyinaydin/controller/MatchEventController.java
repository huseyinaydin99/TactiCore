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
import tr.com.huseyinaydin.dto.matchevent.CreateMatchEventDto;
import tr.com.huseyinaydin.dto.matchevent.DashboardSummaryDto;
import tr.com.huseyinaydin.dto.matchevent.GetByIdMatchEventDto;
import tr.com.huseyinaydin.dto.matchevent.ResultMatchEventDto;
import tr.com.huseyinaydin.dto.matchevent.UpdateMatchEventDto;
import tr.com.huseyinaydin.service.MatchEventService;

import java.util.List;

@RestController
@RequestMapping("/matchevent")
@Validated
@RequiredArgsConstructor
public class MatchEventController {

    private final MatchEventService matchEventService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResultMatchEventDto>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(matchEventService.getAll()));
    }

    @GetMapping("/GetMatchEvent")
    public ResponseEntity<ApiResponse<GetByIdMatchEventDto>> getById(@RequestParam String id) {
        return ResponseEntity.ok(ApiResponse.success(matchEventService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody CreateMatchEventDto dto) {
        matchEventService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Match event created"));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Void>> update(@Valid @RequestBody UpdateMatchEventDto dto) {
        matchEventService.update(dto);
        return ResponseEntity.ok(ApiResponse.success("Match event updated"));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(@RequestParam String id) {
        matchEventService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Match event deleted"));
    }

    @GetMapping("/GetByMatchId")
    public ResponseEntity<ApiResponse<List<ResultMatchEventDto>>> getByMatchId(@RequestParam String id) {
        return ResponseEntity.ok(ApiResponse.success(matchEventService.getByMatchId(id)));
    }

    @GetMapping("/GetDashboardSummary")
    public ResponseEntity<ApiResponse<DashboardSummaryDto>> getDashboardSummary() {
        return ResponseEntity.ok(ApiResponse.success(matchEventService.getDashboardSummary()));
    }
}
