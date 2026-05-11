package tr.com.huseyinaydin.model;

import tr.com.huseyinaydin.dto.match.GetMatchByIdDto;
import tr.com.huseyinaydin.dto.matchevent.ResultMatchEventDto;
import tr.com.huseyinaydin.dto.player.ResultPlayerDto;
import tr.com.huseyinaydin.enums.EventType;

import java.util.Collections;
import java.util.List;

public class MatchEventsViewModel {

    private final GetMatchByIdDto match;
    private final List<ResultMatchEventDto> events;
    private final List<ResultPlayerDto> players;

    public MatchEventsViewModel(GetMatchByIdDto match,
                                List<ResultMatchEventDto> events,
                                List<ResultPlayerDto> players) {
        this.match   = match;
        this.events  = events  != null ? events  : Collections.emptyList();
        this.players = players != null ? players : Collections.emptyList();
    }

    public GetMatchByIdDto getMatch()               { return match; }
    public List<ResultMatchEventDto> getEvents()    { return events; }
    public List<ResultPlayerDto> getPlayers()       { return players; }

    public long getGoalCount() {
        return events.stream().filter(e -> e.getEventType() == EventType.GOAL).count();
    }

    public long getYellowCardCount() {
        return events.stream().filter(e -> e.getEventType() == EventType.YELLOW_CARD).count();
    }

    public long getRedCardCount() {
        return events.stream().filter(e -> e.getEventType() == EventType.RED_CARD).count();
    }

    public long getSubstitutionCount() {
        return events.stream().filter(e -> e.getEventType() == EventType.SUBSTITUTION).count();
    }
}
