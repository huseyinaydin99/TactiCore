package tr.com.huseyinaydin.service.business;

import tr.com.huseyinaydin.dto.player.ResultPlayerDto;
import tr.com.huseyinaydin.entity.Team;

public class PlayerEnricher {

    public static void enrich(ResultPlayerDto dto, Team team) {
        dto.setTeamName(team.getName());
        dto.setTeamLogoUrl(team.getLogoUrl());
    }
}
