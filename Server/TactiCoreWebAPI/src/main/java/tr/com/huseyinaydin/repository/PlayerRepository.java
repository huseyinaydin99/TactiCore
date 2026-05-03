package tr.com.huseyinaydin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tr.com.huseyinaydin.entity.Player;

import java.util.List;

public interface PlayerRepository extends MongoRepository<Player, String> {

    List<Player> findByTeamId(String teamId);

    List<Player> findByIsStarPlayerTrue();

    List<Player> findByTeamIdAndIsActiveTrue(String teamId);
}
