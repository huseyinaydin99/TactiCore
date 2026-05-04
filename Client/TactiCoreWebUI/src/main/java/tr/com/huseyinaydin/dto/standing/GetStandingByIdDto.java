package tr.com.huseyinaydin.dto.standing;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStandingByIdDto {

    private String id;
    private String teamId;
    private String teamName;
    private String teamShortName;
    private String teamLogoUrl;
    private int played;
    private int won;
    private int draw;
    private int lost;
    private int goalsFor;
    private int goalsAgainst;
    private int goalDifference;
    private int points;
    private int position;
    private String form;
}
