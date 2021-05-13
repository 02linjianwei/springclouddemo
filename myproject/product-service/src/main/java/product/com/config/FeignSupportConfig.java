package product.com.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import feign.Feign;
import feign.Target;
import feign.hystrix.SetterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
public class FeignSupportConfig {
    @Bean
    public SetterFactory setterFactory(){
        SetterFactory setterFactory =new SetterFactory() {
            @Override
            public HystrixCommand.Setter create(Target<?> target, Method method) {

                String groupKey = target.name();
                String commandKey = Feign.configKey(target.type(), method);

                HystrixCommandProperties.Setter setter = HystrixCommandProperties.Setter()
                        //设置统计指标60秒为一个时间窗口
                        .withMetricsRollingStatisticalWindowInMilliseconds(1000 * 60)
                        //超过80%失败率
                        .withCircuitBreakerErrorThresholdPercentage(80)
                        //操作5个开启短路器
                        .withCircuitBreakerRequestVolumeThreshold(5)
                        //设置线程隔离
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                        //设置断路器的开启时间为60秒
                        .withCircuitBreakerSleepWindowInMilliseconds(1000 * 60);

                return HystrixCommand.Setter
                        .withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                        .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
                        .andCommandPropertiesDefaults(setter);
            }
        };
        return setterFactory;
    }

}
