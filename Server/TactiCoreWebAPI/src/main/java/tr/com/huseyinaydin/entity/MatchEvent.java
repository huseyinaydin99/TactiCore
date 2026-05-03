package tr.com.huseyinaydin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tr.com.huseyinaydin.entity.enums.EventType;

@Document(collection = "match_events")
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MatchEvent extends BaseEntity {

    @Id
    private String id;
    private String matchId;
    private String teamId;
    private String playerId;
    private int minute;
    private EventType eventType;
    private String description;
}
