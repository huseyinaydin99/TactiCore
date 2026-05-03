package tr.com.huseyinaydin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tr.com.huseyinaydin.entity.enums.MatchStatus;

import java.time.LocalDateTime;

@Document(collection = "matches")
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Match extends BaseEntity {

    @Id
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
    private boolean isFeatured;
}
