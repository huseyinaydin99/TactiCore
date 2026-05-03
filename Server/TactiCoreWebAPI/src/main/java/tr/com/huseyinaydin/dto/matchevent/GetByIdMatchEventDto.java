package tr.com.huseyinaydin.dto.matchevent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.huseyinaydin.entity.enums.EventType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetByIdMatchEventDto {

    private String id;
    private String matchId;
    private String teamId;
    private String teamName;
    private String teamLogoUrl;
    private String playerId;
    private String playerName;
    private String playerImageUrl;
    private int minute;
    private EventType eventType;
    private String description;
}
