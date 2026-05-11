package tr.com.huseyinaydin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private InMemoryUserDetailsManager userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public String registerPage(Model model) {
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
            if (userDetailsManager.userExists(username)) {
                ra.addFlashAttribute("errorMessage", "Bu kullanici adi zaten kullaniliyor.");
                return "redirect:/register";
            }
            UserDetails user = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .roles("ADMIN")
                    .build();
            userDetailsManager.createUser(user);
            ra.addFlashAttribute("successMessage", "Kayit basarili! Giris yapabilirsiniz.");
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", "Kayit sirasinda hata olustu: " + ex.getMessage());
        }
        return "redirect:/login";
    }
}
