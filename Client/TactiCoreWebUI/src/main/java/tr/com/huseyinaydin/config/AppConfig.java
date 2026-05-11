package tr.com.huseyinaydin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final JwtClientInterceptor jwtInterceptor;

    @Bean
    @Profile("!dev")
    public RestTemplate restTemplate() {
        RestTemplate rt = new RestTemplate();
        rt.setInterceptors(List.of(jwtInterceptor));
        return rt;
    }

    // Self-signed sertifika sorununu bypass eder; yalnızca dev profilinde aktiftir.
    @Bean
    @Profile("dev")
    public RestTemplate devRestTemplate() {
        try {
            TrustManager[] trustAll = new TrustManager[]{
                new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {}
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {}
                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
                }
            };

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAll, new SecureRandom());

            var socketFactory = sslContext.getSocketFactory();

            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory() {
                @Override
                protected void prepareConnection(HttpURLConnection connection, String httpMethod)
                        throws IOException {
                    if (connection instanceof HttpsURLConnection httpsConn) {
                        httpsConn.setSSLSocketFactory(socketFactory);
                        httpsConn.setHostnameVerifier((hostname, session) -> true);
                    }
                    super.prepareConnection(connection, httpMethod);
                }
            };

            RestTemplate rt = new RestTemplate(factory);
            rt.setInterceptors(List.of(jwtInterceptor));
            return rt;
        } catch (Exception e) {
            RestTemplate rt = new RestTemplate();
            rt.setInterceptors(List.of(jwtInterceptor));
            return rt;
        }
    }
}
