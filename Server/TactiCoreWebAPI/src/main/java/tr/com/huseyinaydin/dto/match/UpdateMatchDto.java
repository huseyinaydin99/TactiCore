package tr.com.huseyinaydin.dto.match;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UpdateMatchDto {

    @NotBlank
    private String id;

    @NotBlank
    private String homeTeamId;

    @NotBlank
    private String awayTeamId;

    private int homeScore;
    private int awayScore;

    @NotNull
    private LocalDateTime matchDate;

    private String stadium;
    private int week;

    @NotNull
    private MatchStatus status;

    private int minute;
    private boolean isFeatured;
}
