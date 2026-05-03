package tr.com.huseyinaydin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TactiCoreWebApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TactiCoreWebApiApplication.class, args);
    }
}
