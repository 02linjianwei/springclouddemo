package com.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RedissonConfig {
    @Autowired
    private Environment environment;
    @Bean
    public RedissonClient config() {
        Config config = new Config();
        //可以设置传输模式为EPOLL,也可以设置为NIO等
        //config.setTransportMode(TransportMode.NIO);
        //设置服务节点部署模式:集群模式,单一节点模式,主从模式,哨兵模式等
        //config.useClusterServers().addNodeAddress(environment.getProperty("redisson.host.config"),environment.getProperty("redisson.host.config"));
        config.useSingleServer().setAddress(environment.getProperty("redisson.host.config")).setKeepAlive(true);
        return Redisson.create(config);
    }
}
