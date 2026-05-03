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
import tr.com.huseyinaydin.dto.news.CreateNewsDto;
import tr.com.huseyinaydin.dto.news.ResultNewsDto;
import tr.com.huseyinaydin.dto.news.UpdateNewsDto;
import tr.com.huseyinaydin.service.NewsService;

import java.util.List;

@RestController
@RequestMapping("/news")
@Validated
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResultNewsDto>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(newsService.getAll()));
    }

    @GetMapping("/GetNews")
    public ResponseEntity<ApiResponse<ResultNewsDto>> getById(@RequestParam String id) {
        return ResponseEntity.ok(ApiResponse.success(newsService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> create(@Valid @RequestBody CreateNewsDto dto) {
        newsService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("News created"));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Void>> update(@Valid @RequestBody UpdateNewsDto dto) {
        newsService.update(dto);
        return ResponseEntity.ok(ApiResponse.success("News updated"));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(@RequestParam String id) {
        newsService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("News deleted"));
    }

    @GetMapping("/GetMainNews")
    public ResponseEntity<ApiResponse<List<ResultNewsDto>>> getMainNews() {
        return ResponseEntity.ok(ApiResponse.success(newsService.getMainNews()));
    }
}
