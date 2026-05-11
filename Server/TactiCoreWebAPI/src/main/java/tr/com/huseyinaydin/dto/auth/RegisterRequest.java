package tr.com.huseyinaydin.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Kullanici adi bos olamaz")
    @Size(min = 3, message = "Kullanici adi en az 3 karakter olmalidir")
    private String username;

    @NotBlank(message = "Sifre bos olamaz")
    @Size(min = 6, message = "Sifre en az 6 karakter olmalidir")
    private String password;

    @NotBlank(message = "Sifre tekrar bos olamaz")
    private String confirmPassword;
}
