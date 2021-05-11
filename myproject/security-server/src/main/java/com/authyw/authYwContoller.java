package com.authyw;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class authYwContoller {
    /**
     * 当通过acces_token来访问时要解析成OAuth2Authentication,得声明该资源为受保护资源
     * @param oAuth2Authentication
     * @return
     */
    @RequestMapping(value = {"/user"})
    public Map<String, Object> user(OAuth2Authentication oAuth2Authentication) {
        Map<String,Object> userInfo = new HashMap<>();
//        userInfo.put("user",oAuth2Authentication.getUserAuthentication().getPrincipal());
//        userInfo.put("authorities", AuthorityUtils.authorityListToSet(oAuth2Authentication.getUserAuthentication().getAuthorities()));
        return userInfo;
    }
}
