package product.com.productyw.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import product.com.productyw.Entity.User;
@FeignClient("userservice")
public interface UserRemoteClient {
    @RequestMapping(value="/users/{id}",method = RequestMethod.GET)
    User load(@PathVariable(value = "id") Long id);
}
