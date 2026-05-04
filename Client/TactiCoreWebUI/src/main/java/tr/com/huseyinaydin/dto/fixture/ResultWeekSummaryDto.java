package tr.com.huseyinaydin.dto.fixture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultWeekSummaryDto {

    private int week;
    private int totalMatches;
    private int liveCount;
    private int finishedCount;
    private int upcomingCount;
    private List<ResultFixtureDto> fixtures;
}
