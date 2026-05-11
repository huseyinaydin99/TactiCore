package tr.com.huseyinaydin.model;

import tr.com.huseyinaydin.dto.fixture.ResultFeaturedFixtureDto;
import tr.com.huseyinaydin.dto.match.ResultMatchDto;
import tr.com.huseyinaydin.dto.matchevent.DashboardSummaryDto;
import tr.com.huseyinaydin.dto.player.ResultPlayerDto;

import java.util.List;

public class AdminDashboardViewModel {

    private final long liveCount;
    private final long finishedCount;
    private final long upcomingCount;
    private final long totalMatchCount;
    private final int teamCount;
    private final int playerCount;
    private final int currentWeek;
    private final ResultFeaturedFixtureDto featuredMatch;
    private final List<ResultMatchDto> recentMatches;
    private final List<ResultPlayerDto> topScorers;
    private final DashboardSummaryDto eventSummary;

    private AdminDashboardViewModel(Builder b) {
        this.liveCount       = b.liveCount;
        this.finishedCount   = b.finishedCount;
        this.upcomingCount   = b.upcomingCount;
        this.totalMatchCount = b.totalMatchCount;
        this.teamCount       = b.teamCount;
        this.playerCount     = b.playerCount;
        this.currentWeek     = b.currentWeek;
        this.featuredMatch   = b.featuredMatch;
        this.recentMatches   = b.recentMatches;
        this.topScorers      = b.topScorers;
        this.eventSummary    = b.eventSummary;
    }

    public long getLiveCount()                      { return liveCount; }
    public long getFinishedCount()                  { return finishedCount; }
    public long getUpcomingCount()                  { return upcomingCount; }
    public long getTotalMatchCount()                { return totalMatchCount; }
    public int  getTeamCount()                      { return teamCount; }
    public int  getPlayerCount()                    { return playerCount; }
    public int  getCurrentWeek()                    { return currentWeek; }
    public ResultFeaturedFixtureDto getFeaturedMatch()   { return featuredMatch; }
    public List<ResultMatchDto>     getRecentMatches()   { return recentMatches; }
    public List<ResultPlayerDto>    getTopScorers()      { return topScorers; }
    public DashboardSummaryDto      getEventSummary()    { return eventSummary; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private long liveCount;
        private long finishedCount;
        private long upcomingCount;
        private long totalMatchCount;
        private int teamCount;
        private int playerCount;
        private int currentWeek;
        private ResultFeaturedFixtureDto featuredMatch;
        private List<ResultMatchDto>     recentMatches;
        private List<ResultPlayerDto>    topScorers;
        private DashboardSummaryDto      eventSummary;

        private Builder() {}

        public Builder liveCount(long v)           { liveCount = v;       return this; }
        public Builder finishedCount(long v)       { finishedCount = v;   return this; }
        public Builder upcomingCount(long v)       { upcomingCount = v;   return this; }
        public Builder totalMatchCount(long v)     { totalMatchCount = v; return this; }
        public Builder teamCount(int v)            { teamCount = v;       return this; }
        public Builder playerCount(int v)          { playerCount = v;     return this; }
        public Builder currentWeek(int v)          { currentWeek = v;     return this; }
        public Builder featuredMatch(ResultFeaturedFixtureDto v) { featuredMatch = v; return this; }
        public Builder recentMatches(List<ResultMatchDto> v)     { recentMatches = v; return this; }
        public Builder topScorers(List<ResultPlayerDto> v)       { topScorers = v;    return this; }
        public Builder eventSummary(DashboardSummaryDto v)       { eventSummary = v;  return this; }
        public AdminDashboardViewModel build()                   { return new AdminDashboardViewModel(this); }
    }
}
