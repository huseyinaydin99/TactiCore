package tr.com.huseyinaydin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tr.com.huseyinaydin.common.ApiResponse;
import tr.com.huseyinaydin.dto.player.ResultPlayerDto;
import tr.com.huseyinaydin.service.PlayerService;

import java.util.List;

@RestController
@RequestMapping("/player")
@Validated
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResultPlayerDto>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(playerService.getAll()));
    }

    @GetMapping("/GetByTeam")
    public ResponseEntity<ApiResponse<List<ResultPlayerDto>>> getByTeam(@RequestParam String teamId) {
        return ResponseEntity.ok(ApiResponse.success(playerService.getByTeam(teamId)));
    }
}
