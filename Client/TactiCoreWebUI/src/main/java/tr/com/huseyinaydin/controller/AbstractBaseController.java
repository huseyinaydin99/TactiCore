package tr.com.huseyinaydin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import tr.com.huseyinaydin.service.ApiServiceFactory;

import java.time.LocalDate;

public abstract class AbstractBaseController {

    private static final Logger log = LoggerFactory.getLogger(AbstractBaseController.class);

    @Autowired
    protected ApiServiceFactory serviceFactory;

    protected String handleError(Model model, Exception ex, String fallbackView) {
        log.error("Controller hatası [{}]: {}", fallbackView, ex.getMessage(), ex);
        model.addAttribute("errorMessage", ex.getMessage());
        return fallbackView;
    }

    protected void addCommonAttributes(Model model) {
        model.addAttribute("currentYear", LocalDate.now().getYear());
        model.addAttribute("appName", "TactiCore");
    }
}
