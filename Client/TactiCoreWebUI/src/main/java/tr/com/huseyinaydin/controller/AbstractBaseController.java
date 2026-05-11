package tr.com.huseyinaydin.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tr.com.huseyinaydin.service.ApiServiceFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class AbstractBaseController {

    private static final Logger log = LoggerFactory.getLogger(AbstractBaseController.class);

    @Autowired
    protected ApiServiceFactory serviceFactory;

    protected String handleError(Model model, Exception ex, String fallbackView) {
        log.error("Controller hatası [{}]: {}", fallbackView, ex.getMessage(), ex);
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorTime", LocalDateTime.now());
        return "error/error";
    }

    protected void addCommonAttributes(Model model) {
        model.addAttribute("currentYear", LocalDate.now().getYear());
        model.addAttribute("appName", "TactiCore");
        try {
            HttpServletRequest req = ((ServletRequestAttributes)
                    RequestContextHolder.currentRequestAttributes()).getRequest();
            model.addAttribute("currentUri", req.getRequestURI());
        } catch (Exception ignored) {
            model.addAttribute("currentUri", "");
        }
    }
}
