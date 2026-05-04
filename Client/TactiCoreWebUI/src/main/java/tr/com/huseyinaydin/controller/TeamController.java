package tr.com.huseyinaydin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teams")
public class TeamController extends AbstractBaseController {

    @GetMapping
    public String list(Model model) {
        try {
            addCommonAttributes(model);
            model.addAttribute("standings", serviceFactory.getStandingService().getAll());
            return "team/list";
        } catch (Exception ex) {
            return handleError(model, ex, "team/list");
        }
    }
}
