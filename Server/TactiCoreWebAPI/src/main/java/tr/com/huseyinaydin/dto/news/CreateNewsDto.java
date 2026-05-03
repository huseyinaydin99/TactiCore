package tr.com.huseyinaydin.dto.news;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNewsDto {

    @NotBlank
    private String title;

    private String summary;

    @NotBlank
    private String content;

    private String imageUrl;

    @NotBlank
    private String category;

    private boolean isMain;
    private boolean isActive;
}
