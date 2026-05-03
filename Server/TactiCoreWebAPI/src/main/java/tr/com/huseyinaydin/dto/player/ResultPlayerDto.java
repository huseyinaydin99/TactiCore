package tr.com.huseyinaydin.dto.player;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.huseyinaydin.entity.enums.PlayerPosition;

@Data
@Builder
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
    private boolean isStarPlayer;
    private boolean isActive;
    private String teamId;
    private String teamName;
    private String teamLogoUrl;
}
