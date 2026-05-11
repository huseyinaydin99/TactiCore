package tr.com.huseyinaydin.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import tr.com.huseyinaydin.model.JwtAuthenticatedUser;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ServerAuthenticationProvider implements AuthenticationProvider {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${api.base-url}")
    private String baseUrl;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        try {
            Map<String, String> body = Map.of("username", username, "password", password);
            String json = restTemplate.postForObject(baseUrl + "/api/auth/login", body, String.class);

            JsonNode root = objectMapper.readTree(json);
            if (!root.path("success").asBoolean(false)) {
                throw new BadCredentialsException(
                        root.path("message").asText("Kullanici adi veya sifre hatali"));
            }

            JsonNode data = root.path("data");
            String token = data.path("token").asText();
            String role  = data.path("role").asText("ROLE_ADMIN");

            JwtAuthenticatedUser user = new JwtAuthenticatedUser(username, token, role);
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        } catch (BadCredentialsException e) {
            throw e;
        } catch (HttpClientErrorException e) {
            try {
                JsonNode root = objectMapper.readTree(e.getResponseBodyAsString());
                throw new BadCredentialsException(root.path("message").asText("Giris basarisiz"));
            } catch (Exception ignored) {}
            throw new BadCredentialsException("Kullanici adi veya sifre hatali");
        } catch (Exception e) {
            throw new BadCredentialsException("Kimlik dogrulama basarisiz: sunucuya erisilemedi");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
