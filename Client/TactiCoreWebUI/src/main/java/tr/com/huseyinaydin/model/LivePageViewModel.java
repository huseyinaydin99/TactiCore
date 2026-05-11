package tr.com.huseyinaydin.model;

import tr.com.huseyinaydin.dto.fixture.ResultLiveFixtureDto;

import java.util.List;

public class LivePageViewModel {

    private final List<ResultLiveFixtureDto> liveMatches;
    private final MatchDetailViewModel       selectedMatch;
    private final String                     selectedMatchId;

    private LivePageViewModel(Builder b) {
        this.liveMatches     = b.liveMatches;
        this.selectedMatch   = b.selectedMatch;
        this.selectedMatchId = b.selectedMatchId;
    }

    public List<ResultLiveFixtureDto> getLiveMatches()   { return liveMatches; }
    public MatchDetailViewModel       getSelectedMatch()  { return selectedMatch; }
    public String                     getSelectedMatchId() { return selectedMatchId; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private List<ResultLiveFixtureDto> liveMatches;
        private MatchDetailViewModel       selectedMatch;
        private String                     selectedMatchId;

        private Builder() {}

        public Builder liveMatches(List<ResultLiveFixtureDto> v)  { liveMatches     = v; return this; }
        public Builder selectedMatch(MatchDetailViewModel v)       { selectedMatch   = v; return this; }
        public Builder selectedMatchId(String v)                   { selectedMatchId = v; return this; }
        public LivePageViewModel build()                           { return new LivePageViewModel(this); }
    }
}
