package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * 认证与授权服务
 */
@Configuration
public class OAuthConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
@Override
/**
 * 客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息；
 * 认证：即可以通过授权码类型获取令牌,也可以通过密码类型获取令牌
 */
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //通过内存方式设置认证的客户端
    // 配置两个客户端，一个用于password认证一个用于client认证
    clients.inMemory().withClient("client_1")
            .authorizedGrantTypes("client_credentials", "refresh_token")
            .scopes("select")
            .authorities("oauth2")
            .secret("123456")
            .and().withClient("client_2")
            .authorizedGrantTypes("authorization_code","password", "refresh_token")
            .accessTokenValiditySeconds(1000)//访问令牌的有效时长
            .refreshTokenValiditySeconds(1000)//刷新令牌的有效时长
            .scopes("all")
            .authorities("oauth2")
            .redirectUris("http://192.168.0.150:8910/")
            .secret("123456");
    }
@Override
/**
 *  用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)，还有token的存储方式(tokenStore)；
 */
    public void configure(AuthorizationServerEndpointsConfigurer authorizationServerEndpointsConfigurer) {
        //设置用户及认证的实现
        authorizationServerEndpointsConfigurer.authenticationManager(authenticationManager)
                .tokenStore(new InMemoryTokenStore())//使用InMemoryTokenStore将令牌保存在内存中(也可以保存到数据库和redis，默认实现JdbcTokenStore和RedisTokenStore)
                .accessTokenConverter(accessTokenConverter())//使用jwt作为令牌的转换器
                .reuseRefreshTokens(false)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }
    //配置JWT转换器
@Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("secret");//签名密钥
        return converter;
    }

    /**
     * 授权服务器安全认证的相关配置,用于配置spring security，生成对应的安全过滤器调用链,主要控制oauth/**端点的相关访问
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security
                .tokenKeyAccess("permitAll()")//允许所有人请求令牌
                .checkTokenAccess("isAuthenticated()")//已验证的客户端才能请求check_token
                .allowFormAuthenticationForClients();// 允许表单认证


    }
}
