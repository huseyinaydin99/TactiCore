package tr.com.huseyinaydin.service.business;

import tr.com.huseyinaydin.dto.match.GetMatchByIdDto;
import tr.com.huseyinaydin.dto.match.ResultMatchDetailDto;
import tr.com.huseyinaydin.dto.match.ResultMatchDto;
import tr.com.huseyinaydin.entity.Team;

public class MatchEnricher {

    public static void enrich(ResultMatchDto dto, Team homeTeam, Team awayTeam) {
        dto.setHomeTeamName(homeTeam.getName());
        dto.setHomeTeamLogoUrl(homeTeam.getLogoUrl());
        dto.setAwayTeamName(awayTeam.getName());
        dto.setAwayTeamLogoUrl(awayTeam.getLogoUrl());
    }

    public static void enrich(GetMatchByIdDto dto, Team homeTeam, Team awayTeam) {
        dto.setHomeTeamName(homeTeam.getName());
        dto.setHomeTeamLogoUrl(homeTeam.getLogoUrl());
        dto.setAwayTeamName(awayTeam.getName());
        dto.setAwayTeamLogoUrl(awayTeam.getLogoUrl());
    }

    public static void enrich(ResultMatchDetailDto dto, Team homeTeam, Team awayTeam) {
        dto.setHomeTeamName(homeTeam.getName());
        dto.setHomeTeamShortName(homeTeam.getShortName());
        dto.setHomeTeamLogoUrl(homeTeam.getLogoUrl());
        dto.setAwayTeamName(awayTeam.getName());
        dto.setAwayTeamShortName(awayTeam.getShortName());
        dto.setAwayTeamLogoUrl(awayTeam.getLogoUrl());
    }
}
