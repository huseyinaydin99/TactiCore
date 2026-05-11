package tr.com.huseyinaydin.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tr.com.huseyinaydin.constant.ApiConstants;

import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${api.base-url}")
    private String baseUrl;

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Kullanici adi veya sifre hatali.");
        }
        if (logout != null) {
            model.addAttribute("successMessage", "Basariyla cikis yapildi.");
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String confirmPassword,
                           RedirectAttributes ra) {
        if (username == null || username.isBlank()) {
            ra.addFlashAttribute("errorMessage", "Kullanici adi bos olamaz.");
            return "redirect:/register";
        }
        if (password == null || password.isBlank()) {
            ra.addFlashAttribute("errorMessage", "Sifre bos olamaz.");
            return "redirect:/register";
        }
        if (!password.equals(confirmPassword)) {
            ra.addFlashAttribute("errorMessage", "Sifreler eslesmiyor.");
            return "redirect:/register";
        }
        if (password.length() < 6) {
            ra.addFlashAttribute("errorMessage", "Sifre en az 6 karakter olmalidir.");
            return "redirect:/register";
        }

        try {
            Map<String, String> body = Map.of(
                    "username", username,
                    "password", password,
                    "confirmPassword", confirmPassword
            );
            String json = restTemplate.postForObject(baseUrl + ApiConstants.AUTH_REGISTER, body, String.class);
            JsonNode root = objectMapper.readTree(json);
            if (!root.path("success").asBoolean(false)) {
                ra.addFlashAttribute("errorMessage", root.path("message").asText("Kayit basarisiz"));
                return "redirect:/register";
            }
            ra.addFlashAttribute("successMessage", "Kayit basarili! Giris yapabilirsiniz.");
        } catch (HttpClientErrorException e) {
            try {
                JsonNode root = objectMapper.readTree(e.getResponseBodyAsString());
                ra.addFlashAttribute("errorMessage", root.path("message").asText("Kayit basarisiz"));
            } catch (Exception ignored) {
                ra.addFlashAttribute("errorMessage", "Kayit sirasinda hata olustu.");
            }
            return "redirect:/register";
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Sunucuya erisilemedi. Lutfen daha sonra tekrar deneyin.");
            return "redirect:/register";
        }

        return "redirect:/login";
    }
}
