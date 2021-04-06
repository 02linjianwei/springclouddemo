package user.com.usercomment.msgmanage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import user.com.usercomment.Entity.UserMsg;
@Component
public class UserMsgSender {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private Source source;

    public UserMsgSender(Source source) {
        this.source = source;
    }

    public void sendMsg(UserMsg userMsg) {
        this.logger.info("发送用户消息:{}",userMsg);
        this.source.output().send(MessageBuilder.withPayload(userMsg).build());
    }
}
