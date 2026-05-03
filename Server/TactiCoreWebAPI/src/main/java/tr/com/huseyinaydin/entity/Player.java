package tr.com.huseyinaydin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tr.com.huseyinaydin.entity.enums.PlayerPosition;

@Document(collection = "players")
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Player extends BaseEntity {

    @Id
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
}
