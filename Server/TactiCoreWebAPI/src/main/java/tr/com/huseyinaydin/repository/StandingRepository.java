package tr.com.huseyinaydin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tr.com.huseyinaydin.entity.Standing;

import java.util.List;
import java.util.Optional;

public interface StandingRepository extends MongoRepository<Standing, String> {

    List<Standing> findAllByOrderByPositionAsc();

    Optional<Standing> findByTeamId(String teamId);
}
