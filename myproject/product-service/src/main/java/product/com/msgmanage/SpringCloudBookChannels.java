package product.com.msgmanage;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface SpringCloudBookChannels {
    @Input("inboundUserMsg")
    SubscribableChannel userMsgs();
    //入参是自定义通道的名称
    @Output("outboundUserMsg")
    MessageChannel userMsgSender();

}
