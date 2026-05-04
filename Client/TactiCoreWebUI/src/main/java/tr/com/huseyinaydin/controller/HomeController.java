package tr.com.huseyinaydin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tr.com.huseyinaydin.dto.match.ResultMatchDto;
import tr.com.huseyinaydin.dto.player.ResultPlayerDto;
import tr.com.huseyinaydin.model.DashboardViewModel;

import java.util.Comparator;
import java.util.List;

@Controller
public class HomeController extends AbstractBaseController {

    @GetMapping("/")
    public String index(Model model) {
        try {
            addCommonAttributes(model);

            long liveCount     = serviceFactory.getMatchService().getLiveCount();
            long finishedCount = serviceFactory.getMatchService().getFinishedCount();
            long upcomingCount = serviceFactory.getMatchService().getUpcomingCount();

            List<ResultMatchDto> recentMatches = serviceFactory.getMatchService()
                    .getAll().stream().limit(5).toList();

            List<ResultPlayerDto> topScorers = serviceFactory.getPlayerService()
                    .getAll().stream()
                    .sorted(Comparator.comparingInt(ResultPlayerDto::getGoals).reversed())
                    .limit(5)
                    .toList();

            DashboardViewModel dashboard = DashboardViewModel.builder()
                    .liveCount(liveCount)
                    .finishedCount(finishedCount)
                    .upcomingCount(upcomingCount)
                    .recentMatches(recentMatches)
                    .topScorers(topScorers)
                    .eventSummary(serviceFactory.getMatchEventService().getDashboardSummary())
                    .build();

            model.addAttribute("dashboard", dashboard);
            return "home/index";
        } catch (Exception ex) {
            return handleError(model, ex, "home/index");
        }
    }
}
