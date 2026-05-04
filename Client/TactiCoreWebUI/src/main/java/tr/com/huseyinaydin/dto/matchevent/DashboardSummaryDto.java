package tr.com.huseyinaydin.dto.matchevent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryDto {

    private long totalGoals;
    private long totalYellowCards;
    private long totalRedCards;
    private long totalSubstitutions;
    private long totalEvents;
}
