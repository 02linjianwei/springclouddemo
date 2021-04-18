package com.business.rabbitmqdemo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
@Component
public class Publisher {
    private static final Logger log = LoggerFactory.getLogger(Publisher.class);
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void sendMsg() {
        LoginEvent event = new LoginEvent(this,"debug",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        applicationEventPublisher.publishEvent(event);
    }
}
