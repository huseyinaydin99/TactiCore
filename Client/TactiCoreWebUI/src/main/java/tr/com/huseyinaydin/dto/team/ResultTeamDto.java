package tr.com.huseyinaydin.dto.team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultTeamDto {

    private String id;
    private String name;
    private String shortName;
    private String logoUrl;
    private String city;
    private String stadium;
    private boolean active;
}
