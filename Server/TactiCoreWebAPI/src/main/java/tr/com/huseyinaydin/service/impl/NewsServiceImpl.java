package tr.com.huseyinaydin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tr.com.huseyinaydin.dto.news.CreateNewsDto;
import tr.com.huseyinaydin.dto.news.ResultNewsDto;
import tr.com.huseyinaydin.dto.news.UpdateNewsDto;
import tr.com.huseyinaydin.entity.News;
import tr.com.huseyinaydin.exception.ResourceNotFoundException;
import tr.com.huseyinaydin.mapper.NewsMapper;
import tr.com.huseyinaydin.repository.NewsRepository;
import tr.com.huseyinaydin.service.NewsService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    @Cacheable(value = "news", key = "'all'")
    public List<ResultNewsDto> getAll() {
        return newsRepository.findAll().stream()
                .map(newsMapper::toResultDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "news", key = "#id")
    public ResultNewsDto getById(String id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News not found: " + id));
        return newsMapper.toResultDto(news);
    }

    @CacheEvict(value = "news", allEntries = true)
    public void create(CreateNewsDto dto) {
        newsRepository.save(newsMapper.toEntity(dto));
    }

    @CacheEvict(value = "news", allEntries = true)
    public void update(UpdateNewsDto dto) {
        News existing = newsRepository.findById(dto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("News not found: " + dto.getId()));
        News updated = newsMapper.toEntity(dto);
        updated.setCreatedDate(existing.getCreatedDate());
        newsRepository.save(updated);
    }

    @CacheEvict(value = "news", allEntries = true)
    public void delete(String id) {
        newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News not found: " + id));
        newsRepository.deleteById(id);
    }

    @Cacheable(value = "news", key = "'main'")
    public List<ResultNewsDto> getMainNews() {
        return newsRepository.findByIsMainTrue().stream()
                .map(newsMapper::toResultDto)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "news", key = "'active'")
    public List<ResultNewsDto> getActiveNews() {
        return newsRepository.findByIsActiveTrueOrderByCreatedDateDesc().stream()
                .map(newsMapper::toResultDto)
                .collect(Collectors.toList());
    }
}
