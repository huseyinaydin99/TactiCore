package tr.com.huseyinaydin.model;

import tr.com.huseyinaydin.dto.match.ResultMatchDetailDto;
import tr.com.huseyinaydin.dto.matchevent.ResultMatchEventDto;
import tr.com.huseyinaydin.enums.EventType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MatchDetailViewModel {

    private final ResultMatchDetailDto match;
    private final List<ResultMatchEventDto> events;

    private MatchDetailViewModel(Builder builder) {
        this.match = builder.match;
        this.events = builder.events;
    }

    public ResultMatchDetailDto getMatch()            { return match; }
    public List<ResultMatchEventDto> getEvents()      { return events; }

    public List<ResultMatchEventDto> getGoalEvents() {
        if (events == null) return Collections.emptyList();
        return events.stream().filter(e -> e.getEventType() == EventType.GOAL).collect(Collectors.toList());
    }

    public List<ResultMatchEventDto> getCardEvents() {
        if (events == null) return Collections.emptyList();
        return events.stream()
                .filter(e -> e.getEventType() == EventType.YELLOW_CARD || e.getEventType() == EventType.RED_CARD)
                .collect(Collectors.toList());
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private ResultMatchDetailDto match;
        private List<ResultMatchEventDto> events;

        private Builder() {}

        public Builder match(ResultMatchDetailDto match) {
            this.match = match;
            return this;
        }

        public Builder events(List<ResultMatchEventDto> events) {
            this.events = events;
            return this;
        }

        public MatchDetailViewModel build() {
            return new MatchDetailViewModel(this);
        }
    }
}
