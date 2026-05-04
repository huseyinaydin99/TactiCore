package tr.com.huseyinaydin.dto.fixture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.huseyinaydin.enums.MatchStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultLiveFixtureDto {

    private String id;
    private String homeTeamName;
    private String homeTeamLogoUrl;
    private String awayTeamName;
    private String awayTeamLogoUrl;
    private int homeScore;
    private int awayScore;
    private int minute;
    private MatchStatus status;
}
