//package com.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.AsyncConfigurer;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.util.concurrent.Executor;
//
//@Configuration
////@EnableAsync
//public class AsyncConfig implements AsyncConfigurer {
//    private static final Logger log = LoggerFactory.getLogger(AsyncConfig.class);
//    @Override
//    public Executor getAsyncExecutor() {
//        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
//        threadPoolTaskExecutor.setCorePoolSize(3);//核心线程数
//        threadPoolTaskExecutor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());//最大线程数
//        threadPoolTaskExecutor.setQueueCapacity(100);//缓存队列
//        threadPoolTaskExecutor.initialize();
//        return threadPoolTaskExecutor;
//    }
//
//    @Override
//    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//        log.error("================线程执行异常======");
//        return null;
//    }
//}
