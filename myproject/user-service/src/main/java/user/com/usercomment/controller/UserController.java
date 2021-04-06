package user.com.usercomment.controller;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import user.com.usercomment.Entity.User;
import user.com.usercomment.Entity.UserMsg;
import user.com.usercomment.msgmanage.UserMsgSender;

@RestController
@RequestMapping("/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Value("${server.port}")
    public String servicePort;
    @Autowired
    protected UserMsgSender userMsgSender;
    @Autowired
    protected Tracer tracer;

    @RequestMapping("/{id}")
    public User comments(@PathVariable Long id) {
        logger.info("==========user=========");
        User user = new User();
        user.setId(123l);
        user.setAddress("1213@11");
        user.setName("dddd");
        user.setServicePort(servicePort);
        save(user);
        return user;
    }

    public User save(User user) {
        user = new User();
        user.setId(123l);
        user.setAddress("1213@11");
        user.setName("dddd");
        user.setServicePort(servicePort);
        this.sendMsg(UserMsg.UA_UPDATE,user.getId());
        return user;
    }

    protected void sendMsg(String action, Long userId) {
        this.userMsgSender.sendMsg(new UserMsg(action,userId,this.getTraceId()));
    }

    protected String getTraceId() {
        return this.tracer.getCurrentSpan().traceIdString();
    }
}
