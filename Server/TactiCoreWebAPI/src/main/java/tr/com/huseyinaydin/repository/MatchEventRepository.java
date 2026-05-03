package tr.com.huseyinaydin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tr.com.huseyinaydin.entity.MatchEvent;

import java.util.List;

public interface MatchEventRepository extends MongoRepository<MatchEvent, String> {

    List<MatchEvent> findByMatchId(String matchId);

    List<MatchEvent> findByTeamId(String teamId);

    List<MatchEvent> findByMatchIdOrderByMinuteAsc(String matchId);
}
