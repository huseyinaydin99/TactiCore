package tr.com.huseyinaydin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tr.com.huseyinaydin.dto.fixture.ResultLiveFixtureDto;
import tr.com.huseyinaydin.model.LivePageViewModel;
import tr.com.huseyinaydin.model.MatchDetailViewModel;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/live")
public class LiveController extends AbstractBaseController {

    @GetMapping
    public String index(Model model) {
        try {
            addCommonAttributes(model);
            List<ResultLiveFixtureDto> liveMatches = fetchLive();

            String selectedMatchId = null;
            MatchDetailViewModel selectedMatch = null;
            if (!liveMatches.isEmpty()) {
                selectedMatchId = liveMatches.get(0).getId();
                selectedMatch = buildMatchDetail(selectedMatchId);
            }

            model.addAttribute("liveViewModel", LivePageViewModel.builder()
                    .liveMatches(liveMatches)
                    .selectedMatch(selectedMatch)
                    .selectedMatchId(selectedMatchId)
                    .build());
            return "live/index";
        } catch (Exception ex) {
            return handleError(model, ex, "live/index");
        }
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable String id, Model model) {
        try {
            addCommonAttributes(model);
            List<ResultLiveFixtureDto> liveMatches = fetchLive();

            model.addAttribute("liveViewModel", LivePageViewModel.builder()
                    .liveMatches(liveMatches)
                    .selectedMatch(buildMatchDetail(id))
                    .selectedMatchId(id)
                    .build());
            return "live/index";
        } catch (Exception ex) {
            return handleError(model, ex, "live/index");
        }
    }

    private List<ResultLiveFixtureDto> fetchLive() {
        List<ResultLiveFixtureDto> list = serviceFactory.getFixtureService().getLive();
        return list != null ? list : Collections.emptyList();
    }

    private MatchDetailViewModel buildMatchDetail(String id) {
        return MatchDetailViewModel.builder()
                .match(serviceFactory.getMatchService().getDetail(id))
                .events(serviceFactory.getMatchEventService().getByMatchId(id))
                .build();
    }
}
