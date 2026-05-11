package tr.com.huseyinaydin.model;

import tr.com.huseyinaydin.dto.fixture.ResultFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultWeekSummaryDto;

import java.util.List;

public class ResultsPageViewModel {

    private final List<ResultFixtureDto> allMatches;
    private final List<ResultFixtureDto> liveMatches;
    private final List<ResultFixtureDto> finishedMatches;
    private final List<ResultFixtureDto> upcomingMatches;
    private final ResultFixtureDto       featuredMatch;
    private final ResultWeekSummaryDto   summary;
    private final int                    currentWeek;
    private final int                    previousWeek;
    private final int                    nextWeek;
    private final String                 activeTab;

    private ResultsPageViewModel(Builder b) {
        this.allMatches      = b.allMatches;
        this.liveMatches     = b.liveMatches;
        this.finishedMatches = b.finishedMatches;
        this.upcomingMatches = b.upcomingMatches;
        this.featuredMatch   = b.featuredMatch;
        this.summary         = b.summary;
        this.currentWeek     = b.currentWeek;
        this.previousWeek    = b.previousWeek;
        this.nextWeek        = b.nextWeek;
        this.activeTab       = b.activeTab;
    }

    public List<ResultFixtureDto> getAllMatches()      { return allMatches; }
    public List<ResultFixtureDto> getLiveMatches()     { return liveMatches; }
    public List<ResultFixtureDto> getFinishedMatches() { return finishedMatches; }
    public List<ResultFixtureDto> getUpcomingMatches() { return upcomingMatches; }
    public ResultFixtureDto       getFeaturedMatch()   { return featuredMatch; }
    public ResultWeekSummaryDto   getSummary()         { return summary; }
    public int                    getCurrentWeek()     { return currentWeek; }
    public int                    getPreviousWeek()    { return previousWeek; }
    public int                    getNextWeek()        { return nextWeek; }
    public String                 getActiveTab()       { return activeTab; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private List<ResultFixtureDto> allMatches;
        private List<ResultFixtureDto> liveMatches;
        private List<ResultFixtureDto> finishedMatches;
        private List<ResultFixtureDto> upcomingMatches;
        private ResultFixtureDto       featuredMatch;
        private ResultWeekSummaryDto   summary;
        private int                    currentWeek;
        private int                    previousWeek;
        private int                    nextWeek;
        private String                 activeTab = "all";

        private Builder() {}

        public Builder allMatches(List<ResultFixtureDto> v)      { allMatches      = v; return this; }
        public Builder liveMatches(List<ResultFixtureDto> v)     { liveMatches     = v; return this; }
        public Builder finishedMatches(List<ResultFixtureDto> v) { finishedMatches = v; return this; }
        public Builder upcomingMatches(List<ResultFixtureDto> v) { upcomingMatches = v; return this; }
        public Builder featuredMatch(ResultFixtureDto v)          { featuredMatch   = v; return this; }
        public Builder summary(ResultWeekSummaryDto v)            { summary         = v; return this; }
        public Builder currentWeek(int v)                         { currentWeek     = v; return this; }
        public Builder previousWeek(int v)                        { previousWeek    = v; return this; }
        public Builder nextWeek(int v)                            { nextWeek        = v; return this; }
        public Builder activeTab(String v)                        { activeTab       = v; return this; }
        public ResultsPageViewModel build()                       { return new ResultsPageViewModel(this); }
    }
}
