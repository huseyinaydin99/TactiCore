package tr.com.huseyinaydin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tr.com.huseyinaydin.entity.News;

import java.util.List;

public interface NewsRepository extends MongoRepository<News, String> {

    List<News> findByIsActiveTrue();

    List<News> findByIsMainTrue();

    List<News> findByIsActiveTrueOrderByCreatedDateDesc();
}
