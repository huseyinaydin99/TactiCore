package tr.com.huseyinaydin.dto.team;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeamDto {

    @NotBlank
    private String name;

    @NotBlank
    private String shortName;

    private String logoUrl;

    @NotBlank
    private String city;

    @NotBlank
    private String stadium;

    private boolean isActive;
}
