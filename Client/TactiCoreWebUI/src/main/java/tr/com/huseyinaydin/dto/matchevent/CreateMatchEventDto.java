package tr.com.huseyinaydin.dto.matchevent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.huseyinaydin.enums.EventType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatchEventDto {

    private String matchId;
    private String teamId;
    private String playerId;
    private int minute;
    private EventType eventType;
    private String description;
}
