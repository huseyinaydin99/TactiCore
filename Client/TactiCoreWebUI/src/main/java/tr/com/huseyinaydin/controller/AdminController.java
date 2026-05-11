package tr.com.huseyinaydin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tr.com.huseyinaydin.dto.fixture.ResultFeaturedFixtureDto;
import tr.com.huseyinaydin.dto.match.ResultMatchDto;
import tr.com.huseyinaydin.dto.player.ResultPlayerDto;
import tr.com.huseyinaydin.dto.team.ResultTeamDto;
import tr.com.huseyinaydin.model.AdminDashboardViewModel;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractBaseController {

    @GetMapping({"", "/"})
    public String dashboard(Model model) {
        try {
            addCommonAttributes(model);

            long liveCount     = serviceFactory.getMatchService().getLiveCount();
            long finishedCount = serviceFactory.getMatchService().getFinishedCount();
            long upcomingCount = serviceFactory.getMatchService().getUpcomingCount();

            List<ResultMatchDto> allMatches = serviceFactory.getMatchService().getAll();
            if (allMatches == null) allMatches = Collections.emptyList();

            List<ResultPlayerDto> allPlayers = serviceFactory.getPlayerService().getAll();
            if (allPlayers == null) allPlayers = Collections.emptyList();

            List<ResultTeamDto> allTeams = serviceFactory.getTeamService().getAll();
            if (allTeams == null) allTeams = Collections.emptyList();

            List<ResultFeaturedFixtureDto> featured = serviceFactory.getFixtureService().getFeatured();
            ResultFeaturedFixtureDto featuredMatch = (featured != null && !featured.isEmpty()) ? featured.get(0) : null;

            List<ResultMatchDto> recentMatches = allMatches.stream().limit(5).toList();

            List<ResultPlayerDto> topScorers = allPlayers.stream()
                    .sorted(Comparator.comparingInt(ResultPlayerDto::getGoals).reversed())
                    .limit(5)
                    .toList();

            int currentWeek = recentMatches.isEmpty() ? 0 : recentMatches.get(0).getWeek();

            model.addAttribute("adminDashboard", AdminDashboardViewModel.builder()
                    .liveCount(liveCount)
                    .finishedCount(finishedCount)
                    .upcomingCount(upcomingCount)
                    .totalMatchCount(liveCount + finishedCount + upcomingCount)
                    .teamCount(allTeams.size())
                    .playerCount(allPlayers.size())
                    .currentWeek(currentWeek)
                    .featuredMatch(featuredMatch)
                    .recentMatches(recentMatches)
                    .topScorers(topScorers)
                    .eventSummary(serviceFactory.getMatchEventService().getDashboardSummary())
                    .build());

            model.addAttribute("pageTitle", "Dashboard");
            return "admin/dashboard/index";
        } catch (Exception ex) {
            return handleError(model, ex, "admin/dashboard/index");
        }
    }
}
