package tr.com.huseyinaydin.dto.player;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.huseyinaydin.enums.PlayerPosition;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultPlayerDto {

    private String id;
    private String fullName;
    private String shortName;
    private PlayerPosition position;
    private int goals;
    private int assists;
    private String imageUrl;
    private boolean starPlayer;
    private boolean active;
    private String teamId;
    private String teamName;
    private String teamLogoUrl;
}
