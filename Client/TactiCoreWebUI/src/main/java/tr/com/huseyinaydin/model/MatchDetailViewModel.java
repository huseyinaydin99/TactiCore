package tr.com.huseyinaydin.model;

import tr.com.huseyinaydin.dto.match.ResultMatchDetailDto;
import tr.com.huseyinaydin.dto.matchevent.ResultMatchEventDto;

import java.util.List;

public class MatchDetailViewModel {

    private final ResultMatchDetailDto match;
    private final List<ResultMatchEventDto> events;

    private MatchDetailViewModel(Builder builder) {
        this.match = builder.match;
        this.events = builder.events;
    }

    public ResultMatchDetailDto getMatch()            { return match; }
    public List<ResultMatchEventDto> getEvents()      { return events; }

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
