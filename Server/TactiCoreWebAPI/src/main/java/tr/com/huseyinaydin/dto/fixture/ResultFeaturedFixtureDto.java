package tr.com.huseyinaydin.dto.fixture;

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
public class ResultFeaturedFixtureDto {

    private String id;
    private String homeTeamName;
    private String homeTeamLogoUrl;
    private String awayTeamName;
    private String awayTeamLogoUrl;
    private int homeScore;
    private int awayScore;
    private LocalDateTime matchDate;
    private String stadium;
    private MatchStatus status;
    private boolean isFeatured;
}
