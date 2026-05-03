package tr.com.huseyinaydin.service.business;

import tr.com.huseyinaydin.dto.standing.GetStandingByIdDto;
import tr.com.huseyinaydin.dto.standing.ResultStandingDto;
import tr.com.huseyinaydin.entity.Team;

public class StandingEnricher {

    public static void enrich(ResultStandingDto dto, Team team) {
        dto.setTeamName(team.getName());
        dto.setShortName(team.getShortName());
        dto.setLogoUrl(team.getLogoUrl());
    }

    public static void enrich(GetStandingByIdDto dto, Team team) {
        dto.setTeamName(team.getName());
        dto.setTeamShortName(team.getShortName());
        dto.setTeamLogoUrl(team.getLogoUrl());
    }
}
