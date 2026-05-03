package tr.com.huseyinaydin.service.business;

import tr.com.huseyinaydin.dto.fixture.ResultFeaturedFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultLiveFixtureDto;
import tr.com.huseyinaydin.entity.Team;

public class FixtureEnricher {

    public static void enrich(ResultFixtureDto dto, Team homeTeam, Team awayTeam) {
        dto.setHomeTeamName(homeTeam.getName());
        dto.setHomeTeamLogoUrl(homeTeam.getLogoUrl());
        dto.setAwayTeamName(awayTeam.getName());
        dto.setAwayTeamLogoUrl(awayTeam.getLogoUrl());
    }

    public static void enrich(ResultLiveFixtureDto dto, Team homeTeam, Team awayTeam) {
        dto.setHomeTeamName(homeTeam.getName());
        dto.setHomeTeamLogoUrl(homeTeam.getLogoUrl());
        dto.setAwayTeamName(awayTeam.getName());
        dto.setAwayTeamLogoUrl(awayTeam.getLogoUrl());
    }

    public static void enrich(ResultFeaturedFixtureDto dto, Team homeTeam, Team awayTeam) {
        dto.setHomeTeamName(homeTeam.getName());
        dto.setHomeTeamLogoUrl(homeTeam.getLogoUrl());
        dto.setAwayTeamName(awayTeam.getName());
        dto.setAwayTeamLogoUrl(awayTeam.getLogoUrl());
    }
}
