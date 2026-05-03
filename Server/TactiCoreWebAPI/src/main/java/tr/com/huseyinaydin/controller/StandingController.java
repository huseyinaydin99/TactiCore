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
import tr.com.huseyinaydin.dto.standing.CreateStandingDto;
import tr.com.huseyinaydin.dto.standing.GetStandingByIdDto;
import tr.com.huseyinaydin.dto.standing.ResultStandingDto;
import tr.com.huseyinaydin.dto.standing.UpdateStandingDto;
import tr.com.huseyinaydin.service.StandingService;

import java.util.List;

@RestController
@RequestMapping("/standing")
@Validated
@RequiredArgsConstructor
public class StandingController {

    private final StandingService standingService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResultStandingDto>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(standingService.getAll()));
    }

    @GetMapping("/GetStanding")
    public ResponseEntity<ApiResponse<GetStandingByIdDto>> getById(@RequestParam String id) {
        return ResponseEntity.ok(ApiResponse.success(standingService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody CreateStandingDto dto) {
        standingService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Standing created"));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Void>> update(@Valid @RequestBody UpdateStandingDto dto) {
        standingService.update(dto);
        return ResponseEntity.ok(ApiResponse.success("Standing updated"));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(@RequestParam String id) {
        standingService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Standing deleted"));
    }
}
