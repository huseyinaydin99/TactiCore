package tr.com.huseyinaydin.dto.fixture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.huseyinaydin.enums.MatchStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultFixtureDto {

    private String id;
    private String homeTeamId;
    private String homeTeamName;
    private String homeTeamLogoUrl;
    private String awayTeamId;
    private String awayTeamName;
    private String awayTeamLogoUrl;
    private int homeScore;
    private int awayScore;
    private LocalDateTime matchDate;
    private String stadium;
    private int week;
    private MatchStatus status;
    private int minute;
    private boolean featured;
}
