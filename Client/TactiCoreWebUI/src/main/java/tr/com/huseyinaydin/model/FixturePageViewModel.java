package tr.com.huseyinaydin.model;

import tr.com.huseyinaydin.dto.fixture.ResultFeaturedFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultLiveFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultWeekSummaryDto;

import java.util.List;

public class FixturePageViewModel {

    private final List<ResultFixtureDto> fixtures;
    private final List<ResultLiveFixtureDto> liveMatches;
    private final List<ResultFeaturedFixtureDto> featuredMatches;
    private final ResultWeekSummaryDto summary;
    private final int currentWeek;
    private final int previousWeek;
    private final int nextWeek;

    private FixturePageViewModel(Builder builder) {
        this.fixtures = builder.fixtures;
        this.liveMatches = builder.liveMatches;
        this.featuredMatches = builder.featuredMatches;
        this.summary = builder.summary;
        this.currentWeek = builder.currentWeek;
        this.previousWeek = builder.previousWeek;
        this.nextWeek = builder.nextWeek;
    }

    public List<ResultFixtureDto> getFixtures()                   { return fixtures; }
    public List<ResultLiveFixtureDto> getLiveMatches()            { return liveMatches; }
    public List<ResultFeaturedFixtureDto> getFeaturedMatches()    { return featuredMatches; }
    public ResultWeekSummaryDto getSummary()                      { return summary; }
    public int getCurrentWeek()                                   { return currentWeek; }
    public int getPreviousWeek()                                  { return previousWeek; }
    public int getNextWeek()                                      { return nextWeek; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private List<ResultFixtureDto> fixtures;
        private List<ResultLiveFixtureDto> liveMatches;
        private List<ResultFeaturedFixtureDto> featuredMatches;
        private ResultWeekSummaryDto summary;
        private int currentWeek;
        private int previousWeek;
        private int nextWeek;

        private Builder() {}

        public Builder fixtures(List<ResultFixtureDto> fixtures) {
            this.fixtures = fixtures;
            return this;
        }

        public Builder liveMatches(List<ResultLiveFixtureDto> liveMatches) {
            this.liveMatches = liveMatches;
            return this;
        }

        public Builder featuredMatches(List<ResultFeaturedFixtureDto> featuredMatches) {
            this.featuredMatches = featuredMatches;
            return this;
        }

        public Builder summary(ResultWeekSummaryDto summary) {
            this.summary = summary;
            return this;
        }

        public Builder currentWeek(int currentWeek) {
            this.currentWeek = currentWeek;
            return this;
        }

        public Builder previousWeek(int previousWeek) {
            this.previousWeek = previousWeek;
            return this;
        }

        public Builder nextWeek(int nextWeek) {
            this.nextWeek = nextWeek;
            return this;
        }

        public FixturePageViewModel build() {
            return new FixturePageViewModel(this);
        }
    }
}
