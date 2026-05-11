package tr.com.huseyinaydin.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Kullanici adi bos olamaz")
    private String username;

    @NotBlank(message = "Sifre bos olamaz")
    private String password;
}
