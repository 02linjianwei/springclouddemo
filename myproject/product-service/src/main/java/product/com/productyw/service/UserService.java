package product.com.productyw.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import product.com.productyw.Entity.User;

//@FeignClient("USERSERVICE")

public interface UserService {
    User comments(Long id);
    User load(Long id);
}
