package tr.com.huseyinaydin.constant;

public final class ApiConstants {

    private ApiConstants() {}

    // ── Team ──────────────────────────────────────────────────────────────────
    public static final String TEAM_BASE        = "/api/team";
    public static final String TEAM_GET_BY_ID   = "/api/team/GetTeam";

    // ── Match ─────────────────────────────────────────────────────────────────
    public static final String MATCH_BASE           = "/api/match";
    public static final String MATCH_GET_BY_ID      = "/api/match/GetMatch";
    public static final String MATCH_DETAIL         = "/api/match/GetMatchDetail";
    public static final String MATCH_LIVE           = "/api/match/LiveMatches";
    public static final String MATCH_FINISHED       = "/api/match/FinishedMatches";
    public static final String MATCH_UPCOMING       = "/api/match/UpcomingMatches";
    public static final String MATCH_FEATURED       = "/api/match/FeaturedMatch";
    public static final String MATCH_LIVE_COUNT     = "/api/match/LiveCount";
    public static final String MATCH_FINISHED_COUNT = "/api/match/FinishedCount";
    public static final String MATCH_UPCOMING_COUNT = "/api/match/UpcomingCount";

    // ── Player ────────────────────────────────────────────────────────────────
    public static final String PLAYER_BASE = "/api/player";

    // ── Standing ──────────────────────────────────────────────────────────────
    public static final String STANDING_BASE      = "/api/standing";
    public static final String STANDING_GET_BY_ID = "/api/standing/GetStanding";

    // ── Fixture ───────────────────────────────────────────────────────────────
    public static final String FIXTURE_BASE         = "/api/fixture";
    public static final String FIXTURE_BY_WEEK      = "/api/fixture/GetByWeek";
    public static final String FIXTURE_LIVE         = "/api/fixture/GetLiveMatches";
    public static final String FIXTURE_FEATURED     = "/api/fixture/GetFeaturedMatches";
    public static final String FIXTURE_WEEK_SUMMARY = "/api/fixture/GetWeekSummary";

    // ── MatchEvent ────────────────────────────────────────────────────────────
    public static final String MATCH_EVENT_BASE      = "/api/matchevent";
    public static final String MATCH_EVENT_GET_BY_ID = "/api/matchevent/GetMatchEvent";
    public static final String MATCH_EVENT_BY_MATCH  = "/api/matchevent/GetByMatchId";
    public static final String DASHBOARD_SUMMARY     = "/api/matchevent/GetDashboardSummary";
}
