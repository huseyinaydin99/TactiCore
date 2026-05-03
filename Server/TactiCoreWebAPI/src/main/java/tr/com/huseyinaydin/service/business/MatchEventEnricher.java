package tr.com.huseyinaydin.service.business;

import tr.com.huseyinaydin.dto.matchevent.GetByIdMatchEventDto;
import tr.com.huseyinaydin.dto.matchevent.ResultMatchEventDto;
import tr.com.huseyinaydin.entity.Player;
import tr.com.huseyinaydin.entity.Team;

public class MatchEventEnricher {

    public static void enrich(ResultMatchEventDto dto, Team team, Player player) {
        dto.setTeamName(team.getName());
        dto.setTeamLogoUrl(team.getLogoUrl());
        if (player != null) {
            dto.setPlayerName(player.getFullName());
            dto.setPlayerImageUrl(player.getImageUrl());
        }
    }

    public static void enrich(GetByIdMatchEventDto dto, Team team, Player player) {
        dto.setTeamName(team.getName());
        dto.setTeamLogoUrl(team.getLogoUrl());
        if (player != null) {
            dto.setPlayerName(player.getFullName());
            dto.setPlayerImageUrl(player.getImageUrl());
        }
    }
}
