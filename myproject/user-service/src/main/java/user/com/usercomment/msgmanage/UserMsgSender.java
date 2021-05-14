package user.com.usercomment.msgmanage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import user.com.usercomment.Entity.UserMsg;
@Component
public class UserMsgSender {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MessageChannel outboundUserMsg;


    public void sendMsg(UserMsg userMsg) {
        this.logger.info("发送用户消息:{}",userMsg);
        this.outboundUserMsg.send(MessageBuilder.withPayload(userMsg).build());
    }

}
