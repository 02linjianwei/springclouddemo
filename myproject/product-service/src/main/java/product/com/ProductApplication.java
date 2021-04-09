package product.com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import product.com.msgmanage.SpringCloudBookChannels;
import product.com.productyw.Entity.UserMsg;

//EnableFeignClients：注意不在此根路径下得配：basePackages指定包路径
@EnableResourceServer
@EnableFeignClients
@EnableDiscoveryClient
@EnableHystrix
@EnableBinding(SpringCloudBookChannels.class)
@SpringBootApplication
public class ProductApplication {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
@StreamListener("inboundUserMsg")
    public void onUserMsgSink(UserMsg userMsg) {
        this.logger.info("receive user msg:{}",userMsg);
    }
}
