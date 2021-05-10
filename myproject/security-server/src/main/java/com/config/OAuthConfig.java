package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
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
    clients.inMemory().withClient("springclouddemo")
            .secret("scdsecret")
            .authorizedGrantTypes("client_credentials", "refresh_token","password")
            .scopes("all");
    }
@Override
/**
 *  用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)，还有token的存储方式(tokenStore)；
 */
    public void configure(AuthorizationServerEndpointsConfigurer endpointsConfigurer) {
        //设置用户及认证的实现
       endpointsConfigurer.authenticationManager(authenticationManager);
       endpointsConfigurer.userDetailsService(userDetailsService);
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
