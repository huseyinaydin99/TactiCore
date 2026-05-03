package tr.com.huseyinaydin.dto.matchevent;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.com.huseyinaydin.entity.enums.EventType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMatchEventDto {

    @NotBlank
    private String id;

    @NotBlank
    private String matchId;

    @NotBlank
    private String teamId;

    private String playerId;
    private int minute;

    @NotNull
    private EventType eventType;

    private String description;
}
