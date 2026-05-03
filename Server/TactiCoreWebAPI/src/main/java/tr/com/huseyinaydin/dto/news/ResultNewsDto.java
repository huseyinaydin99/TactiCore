package tr.com.huseyinaydin.dto.news;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultNewsDto {

    private String id;
    private String title;
    private String summary;
    private String content;
    private String imageUrl;
    private String category;
    private boolean isMain;
    private boolean isActive;
    private LocalDateTime createdDate;
}
