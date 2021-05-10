package com.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class OAuthWebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    /**
     * 设置用户认证，并赋于角色
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    //通过内存方式设置用户信息
    authenticationManagerBuilder.inMemoryAuthentication()
            .withUser("zhangsan")
            .password("password")
            .roles("USER")
            .and()
            .withUser("superAdmin")
            .password("superPwd")
            .roles("USER","ADMIN");
    }

    /**
     * 通过角色匹配其请求的url是否有权限访问
     * @param
     * @throws Exception
     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//    //允许访问/oauth授权接口
//        http.csrf().disable();
//        http.requestMatchers().anyRequest()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/oauth/**").permitAll();
//    }
@Bean
    public PasswordEncoder passwordEncoder() {
    //配置密码解码器
    return NoOpPasswordEncoder.getInstance();
    }
}
