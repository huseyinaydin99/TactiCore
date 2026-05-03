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
public class ResultMatchDto {

    private String id;
    private String homeTeamName;
    private String homeTeamLogoUrl;
    private String awayTeamName;
    private String awayTeamLogoUrl;
    private int homeScore;
    private int awayScore;
    private LocalDateTime matchDate;
    private int week;
    private MatchStatus status;
    private boolean isFeatured;
}
