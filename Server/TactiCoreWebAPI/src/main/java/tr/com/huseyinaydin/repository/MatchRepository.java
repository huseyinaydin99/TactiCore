package tr.com.huseyinaydin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tr.com.huseyinaydin.entity.Match;
import tr.com.huseyinaydin.entity.enums.MatchStatus;

import java.util.List;

public interface MatchRepository extends MongoRepository<Match, String> {

    List<Match> findByStatus(MatchStatus status);

    List<Match> findByStatusOrderByMatchDateAsc(MatchStatus status);

    List<Match> findByIsFeaturedTrue();

    List<Match> findByWeek(int week);

    List<Match> findByHomeTeamIdOrAwayTeamId(String homeTeamId, String awayTeamId);

    List<Match> findByStatusIn(List<MatchStatus> statuses);
}
