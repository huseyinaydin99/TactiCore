package tr.com.huseyinaydin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "tr.com.huseyinaydin.repository.auth")
public class JpaConfig {
}
