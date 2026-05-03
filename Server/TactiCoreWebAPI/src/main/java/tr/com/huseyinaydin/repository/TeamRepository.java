package tr.com.huseyinaydin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tr.com.huseyinaydin.entity.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends MongoRepository<Team, String> {

    List<Team> findByIsActiveTrue();

    Optional<Team> findByName(String name);
}
