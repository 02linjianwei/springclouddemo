package product.com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        //httpSecurity.authorizeRequests().anyRequest().authenticated();//所有请求
        //httpSecurity.authorizeRequests().antMatchers("/user/**").authenticated();//user路径都要认证
    }
    @Override
    public void configure(ResourceServerSecurityConfigurer resourceServerSecurityConfigurer)  {
        resourceServerSecurityConfigurer.tokenStore(new JwtTokenStore(accessTokenConverter())).stateless(true);
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setAccessTokenConverter(accessTokenConverter());
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() != 400) {
                    super.handleError(response);
                }
            }
        });
        remoteTokenServices.setRestTemplate(restTemplate);
        remoteTokenServices.setCheckTokenEndpointUrl("http://securityservice/oauth/check_token");
        remoteTokenServices.setClientId("client_2");
        remoteTokenServices.setClientSecret("123456");
        resourceServerSecurityConfigurer.tokenServices(remoteTokenServices).stateless(true);
    }
    //配置JWT转换器
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("secret");//签名密钥
        return converter;
    }
}
