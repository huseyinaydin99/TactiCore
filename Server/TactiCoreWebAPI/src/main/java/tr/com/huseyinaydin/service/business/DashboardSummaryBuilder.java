package tr.com.huseyinaydin.service.business;

import tr.com.huseyinaydin.dto.matchevent.DashboardSummaryDto;
import tr.com.huseyinaydin.entity.MatchEvent;
import tr.com.huseyinaydin.entity.enums.EventType;

import java.util.List;

public class DashboardSummaryBuilder {

    public static DashboardSummaryDto build(List<MatchEvent> events) {
        long totalGoals = events.stream().filter(e -> e.getEventType() == EventType.GOAL).count();
        long totalYellowCards = events.stream().filter(e -> e.getEventType() == EventType.YELLOW_CARD).count();
        long totalRedCards = events.stream().filter(e -> e.getEventType() == EventType.RED_CARD).count();
        long totalSubstitutions = events.stream().filter(e -> e.getEventType() == EventType.SUBSTITUTION).count();

        return DashboardSummaryDto.builder()
                .totalGoals(totalGoals)
                .totalYellowCards(totalYellowCards)
                .totalRedCards(totalRedCards)
                .totalSubstitutions(totalSubstitutions)
                .totalEvents(events.size())
                .build();
    }
}
