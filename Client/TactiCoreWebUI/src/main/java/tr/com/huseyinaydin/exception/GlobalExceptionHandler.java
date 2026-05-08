package tr.com.huseyinaydin.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(HttpClientErrorException.class)
    public String handleClientError(HttpClientErrorException ex, Model model) {
        model.addAttribute("statusCode", ex.getStatusCode().value());
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorTime", LocalDateTime.now());
        return "error/error";
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public String handleServerError(HttpServerErrorException ex, Model model) {
        model.addAttribute("statusCode", ex.getStatusCode().value());
        model.addAttribute("errorMessage", "API servisi geçici olarak kullanılamıyor");
        model.addAttribute("errorTime", LocalDateTime.now());
        return "error/error";
    }

    @ExceptionHandler(ResourceAccessException.class)
    public String handleResourceAccess(ResourceAccessException ex, Model model) {
        model.addAttribute("errorMessage",
                "Bağlantı kurulamadı: API çalışmıyor olabilir. Lütfen daha sonra tekrar deneyin.");
        model.addAttribute("errorTime", LocalDateTime.now());
        return "error/error";
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public void handleNoResource(NoResourceFoundException ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model) {
        log.error("Beklenmedik hata oluştu", ex);
        model.addAttribute("errorMessage", "Beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyin.");
        model.addAttribute("errorTime", LocalDateTime.now());
        return "error/error";
    }
}
