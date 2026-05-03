package tr.com.huseyinaydin.dto.match;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.huseyinaydin.entity.enums.MatchStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultMatchDetailDto {

    private String id;
    private String homeTeamId;
    private String homeTeamName;
    private String homeTeamShortName;
    private String homeTeamLogoUrl;
    private String awayTeamId;
    private String awayTeamName;
    private String awayTeamShortName;
    private String awayTeamLogoUrl;
    private int homeScore;
    private int awayScore;
    private LocalDateTime matchDate;
    private String stadium;
    private int week;
    private MatchStatus status;
    private int minute;
    private boolean isFeatured;
}
