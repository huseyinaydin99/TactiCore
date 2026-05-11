package tr.com.huseyinaydin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tr.com.huseyinaydin.dto.fixture.ResultFixtureDto;
import tr.com.huseyinaydin.dto.fixture.ResultWeekSummaryDto;
import tr.com.huseyinaydin.enums.MatchStatus;
import tr.com.huseyinaydin.model.ResultsPageViewModel;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/results")
public class ResultsController extends AbstractBaseController {

    private static final int MIN_WEEK = 1;
    private static final int MAX_WEEK = 38;

    @GetMapping
    public String index(
            @RequestParam(defaultValue = "33") int week,
            @RequestParam(defaultValue = "all") String tab,
            Model model) {
        try {
            addCommonAttributes(model);

            int weekNum      = Math.max(MIN_WEEK, Math.min(MAX_WEEK, week));
            int previousWeek = Math.max(weekNum - 1, MIN_WEEK);
            int nextWeek     = Math.min(weekNum + 1, MAX_WEEK);

            ResultWeekSummaryDto summary = serviceFactory.getFixtureService().getWeekSummary(weekNum);

            List<ResultFixtureDto> allMatches = (summary != null && summary.getFixtures() != null)
                    ? summary.getFixtures()
                    : Collections.emptyList();

            List<ResultFixtureDto> liveMatches = allMatches.stream()
                    .filter(m -> MatchStatus.LIVE.equals(m.getStatus()))
                    .collect(Collectors.toList());

            List<ResultFixtureDto> finishedMatches = allMatches.stream()
                    .filter(m -> MatchStatus.FINISHED.equals(m.getStatus()))
                    .collect(Collectors.toList());

            List<ResultFixtureDto> upcomingMatches = allMatches.stream()
                    .filter(m -> MatchStatus.UPCOMING.equals(m.getStatus()))
                    .collect(Collectors.toList());

            ResultFixtureDto featuredMatch = allMatches.stream()
                    .filter(ResultFixtureDto::isFeatured)
                    .findFirst()
                    .orElse(null);

            ResultsPageViewModel viewModel = ResultsPageViewModel.builder()
                    .allMatches(allMatches)
                    .liveMatches(liveMatches)
                    .finishedMatches(finishedMatches)
                    .upcomingMatches(upcomingMatches)
                    .featuredMatch(featuredMatch)
                    .summary(summary)
                    .currentWeek(weekNum)
                    .previousWeek(previousWeek)
                    .nextWeek(nextWeek)
                    .activeTab(tab)
                    .build();

            model.addAttribute("resultsViewModel", viewModel);
            return "result/index";
        } catch (Exception ex) {
            return handleError(model, ex, "result/index");
        }
    }
}
