package tr.com.huseyinaydin.service.business;

import tr.com.huseyinaydin.dto.fixture.ResultFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultWeekSummaryDto;
import tr.com.huseyinaydin.entity.Match;
import tr.com.huseyinaydin.entity.enums.MatchStatus;

import java.util.List;

public class FixtureSummaryBuilder {

    public static ResultWeekSummaryDto build(int week, List<Match> matches, List<ResultFixtureDto> fixtures) {
        long liveCount = matches.stream().filter(m -> m.getStatus() == MatchStatus.LIVE).count();
        long finishedCount = matches.stream().filter(m -> m.getStatus() == MatchStatus.FINISHED).count();
        long upcomingCount = matches.stream().filter(m -> m.getStatus() == MatchStatus.UPCOMING).count();

        return ResultWeekSummaryDto.builder()
                .week(week)
                .totalMatches(matches.size())
                .liveCount((int) liveCount)
                .finishedCount((int) finishedCount)
                .upcomingCount((int) upcomingCount)
                .fixtures(fixtures)
                .build();
    }
}
