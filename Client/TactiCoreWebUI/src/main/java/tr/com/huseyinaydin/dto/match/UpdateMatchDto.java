package tr.com.huseyinaydin.dto.match;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.huseyinaydin.enums.MatchStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMatchDto {

    private String id;
    private String homeTeamId;
    private String awayTeamId;
    private int homeScore;
    private int awayScore;
    private LocalDateTime matchDate;
    private String stadium;
    private int week;
    private MatchStatus status;
    private int minute;
    private boolean featured;
}
