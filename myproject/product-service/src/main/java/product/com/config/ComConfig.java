package product.com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import product.com.interceptor.JWTOAuthTokenInterceptor;

import java.util.Collections;
import java.util.List;

@Configuration
public class ComConfig {
//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//@Bean
//    public OAuth2RestTemplate oAuth2RestTemplate(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails) {
//        return new OAuth2RestTemplate(oAuth2ProtectedResourceDetails,oAuth2ClientContext);
//    }
@Bean
@Primary
//@LoadBalanced
public RestTemplate oAuth2RestTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    List interceptors = restTemplate.getInterceptors();
    if (interceptors == null) {
        restTemplate.setInterceptors(
                Collections.singletonList(new JWTOAuthTokenInterceptor())
        );
    } else {
        interceptors.add(new JWTOAuthTokenInterceptor());
        restTemplate.setInterceptors(interceptors);
    }
    return restTemplate;
}
}
