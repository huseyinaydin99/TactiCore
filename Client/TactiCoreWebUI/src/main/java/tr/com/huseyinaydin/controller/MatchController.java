package tr.com.huseyinaydin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tr.com.huseyinaydin.model.MatchDetailViewModel;

@Controller
@RequestMapping("/matches")
public class MatchController extends AbstractBaseController {

    @GetMapping
    public String list(Model model) {
        try {
            addCommonAttributes(model);
            model.addAttribute("matches", serviceFactory.getMatchService().getAll());
            return "match/list";
        } catch (Exception ex) {
            return handleError(model, ex, "match/list");
        }
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable String id, Model model) {
        try {
            addCommonAttributes(model);

            MatchDetailViewModel viewModel = MatchDetailViewModel.builder()
                    .match(serviceFactory.getMatchService().getDetail(id))
                    .events(serviceFactory.getMatchEventService().getByMatchId(id))
                    .build();

            model.addAttribute("matchDetail", viewModel);
            return "match/detail";
        } catch (Exception ex) {
            return handleError(model, ex, "match/list");
        }
    }
}
