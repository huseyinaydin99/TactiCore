package tr.com.huseyinaydin.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ApiServiceFactory {

    private final TeamService teamService;
    private final MatchService matchService;
    private final PlayerService playerService;
    private final StandingService standingService;
    private final FixtureService fixtureService;
    private final MatchEventService matchEventService;

    public ApiServiceFactory(
            @Lazy TeamService teamService, //şimdilik hatalı bir sonraki commit'de düzelir.
            @Lazy MatchService matchService,
            @Lazy PlayerService playerService,
            @Lazy StandingService standingService,
            @Lazy FixtureService fixtureService,
            @Lazy MatchEventService matchEventService) {
        this.teamService = teamService;
        this.matchService = matchService;
        this.playerService = playerService;
        this.standingService = standingService;
        this.fixtureService = fixtureService;
        this.matchEventService = matchEventService;
    }

    public TeamService getTeamService()               { return teamService; }
    public MatchService getMatchService()             { return matchService; }
    public PlayerService getPlayerService()           { return playerService; }
    public StandingService getStandingService()       { return standingService; }
    public FixtureService getFixtureService()         { return fixtureService; }
    public MatchEventService getMatchEventService()   { return matchEventService; }
}
