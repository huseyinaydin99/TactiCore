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
import tr.com.huseyinaydin.dto.team.CreateTeamDto;
import tr.com.huseyinaydin.dto.team.GetTeamByIdDto;
import tr.com.huseyinaydin.dto.team.ResultTeamDto;
import tr.com.huseyinaydin.dto.team.UpdateTeamDto;
import tr.com.huseyinaydin.service.TeamService;

import java.util.List;

@RestController
@RequestMapping("/team")
@Validated
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResultTeamDto>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(teamService.getAll()));
    }

    @GetMapping("/GetTeam")
    public ResponseEntity<ApiResponse<GetTeamByIdDto>> getById(@RequestParam String id) {
        return ResponseEntity.ok(ApiResponse.success(teamService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody CreateTeamDto dto) {
        teamService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Team created"));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Void>> update(@Valid @RequestBody UpdateTeamDto dto) {
        teamService.update(dto);
        return ResponseEntity.ok(ApiResponse.success("Team updated"));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(@RequestParam String id) {
        teamService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Team deleted"));
    }
}
