package user.com.usercomment.controller;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user.com.usercomment.Entity.User;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Value("${server.port}")
    public String servicePort;
    @RequestMapping("/{id}")
    public User comments(@PathVariable Long id) {
        User user = new User();
        user.setId(123l);
        user.setAddress("1213@11");
        user.setName("dddd");
        user.setServicePort(servicePort);
        return user;
    }
}
