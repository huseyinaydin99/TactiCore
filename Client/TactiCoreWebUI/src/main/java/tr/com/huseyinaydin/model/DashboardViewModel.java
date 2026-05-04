package tr.com.huseyinaydin.model;

import tr.com.huseyinaydin.dto.match.ResultMatchDto;
import tr.com.huseyinaydin.dto.matchevent.DashboardSummaryDto;
import tr.com.huseyinaydin.dto.player.ResultPlayerDto;

import java.util.List;

public class DashboardViewModel {

    private final long liveCount;
    private final long finishedCount;
    private final long upcomingCount;
    private final List<ResultMatchDto> recentMatches;
    private final List<ResultPlayerDto> topScorers;
    private final DashboardSummaryDto eventSummary;

    private DashboardViewModel(Builder builder) {
        this.liveCount = builder.liveCount;
        this.finishedCount = builder.finishedCount;
        this.upcomingCount = builder.upcomingCount;
        this.recentMatches = builder.recentMatches;
        this.topScorers = builder.topScorers;
        this.eventSummary = builder.eventSummary;
    }

    public long getLiveCount()                        { return liveCount; }
    public long getFinishedCount()                    { return finishedCount; }
    public long getUpcomingCount()                    { return upcomingCount; }
    public List<ResultMatchDto> getRecentMatches()    { return recentMatches; }
    public List<ResultPlayerDto> getTopScorers()      { return topScorers; }
    public DashboardSummaryDto getEventSummary()      { return eventSummary; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private long liveCount;
        private long finishedCount;
        private long upcomingCount;
        private List<ResultMatchDto> recentMatches;
        private List<ResultPlayerDto> topScorers;
        private DashboardSummaryDto eventSummary;

        private Builder() {}

        public Builder liveCount(long liveCount) {
            this.liveCount = liveCount;
            return this;
        }

        public Builder finishedCount(long finishedCount) {
            this.finishedCount = finishedCount;
            return this;
        }

        public Builder upcomingCount(long upcomingCount) {
            this.upcomingCount = upcomingCount;
            return this;
        }

        public Builder recentMatches(List<ResultMatchDto> recentMatches) {
            this.recentMatches = recentMatches;
            return this;
        }

        public Builder topScorers(List<ResultPlayerDto> topScorers) {
            this.topScorers = topScorers;
            return this;
        }

        public Builder eventSummary(DashboardSummaryDto eventSummary) {
            this.eventSummary = eventSummary;
            return this;
        }

        public DashboardViewModel build() {
            return new DashboardViewModel(this);
        }
    }
}
