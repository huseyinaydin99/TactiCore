package tr.com.huseyinaydin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tr.com.huseyinaydin.model.FixturePageViewModel;

@Controller
@RequestMapping("/fixtures")
public class FixtureController extends AbstractBaseController {

    private static final int DEFAULT_WEEK = 33;
    private static final int MIN_WEEK     = 1;
    private static final int MAX_WEEK     = 38;

    @GetMapping
    public String index(
            @RequestParam(defaultValue = "33.Hafta") String week,
            Model model) {
        try {
            addCommonAttributes(model);

            int weekNum      = parseWeek(week);
            int previousWeek = Math.max(weekNum - 1, MIN_WEEK);
            int nextWeek     = Math.min(weekNum + 1, MAX_WEEK);

            FixturePageViewModel viewModel = FixturePageViewModel.builder()
                    .fixtures(serviceFactory.getFixtureService().getByWeek(weekNum))
                    .liveMatches(serviceFactory.getFixtureService().getLive())
                    .featuredMatches(serviceFactory.getFixtureService().getFeatured())
                    .summary(serviceFactory.getFixtureService().getWeekSummary(weekNum))
                    .currentWeek(weekNum)
                    .previousWeek(previousWeek)
                    .nextWeek(nextWeek)
                    .build();

            model.addAttribute("fixturePageViewModel", viewModel);
            return "fixture/index";
        } catch (Exception ex) {
            return handleError(model, ex, "fixture/index");
        }
    }

    private int parseWeek(String week) {
        try {
            return Integer.parseInt(week.replace(".Hafta", "").trim());
        } catch (NumberFormatException e) {
            return DEFAULT_WEEK;
        }
    }
}
