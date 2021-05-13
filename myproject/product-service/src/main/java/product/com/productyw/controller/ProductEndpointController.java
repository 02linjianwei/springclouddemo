package product.com.productyw.controller;

import com.google.common.collect.Lists;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import product.com.productyw.Entity.Product;
import product.com.productyw.Entity.ProductComment;
import product.com.productyw.Entity.User;
import product.com.productyw.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductEndpointController {
    protected Logger logger = LoggerFactory.getLogger(ProductEndpointController.class);
    @Value("${server.port}")
    public String servicePort;
    @Autowired
    private RestTemplate oAuth2RestTemplate;

    @Autowired
    private UserService userService;
    public List<Product> list() {
      List<Product> list =  Lists.newArrayList();
      Product product = new Product("java",123l);
      list.add(product);
      return list;
    }
    @RequestMapping("/{id}")
    public Product detail(@PathVariable Long id) {
        Product product = new Product("java",123l);
        return product;
    }
    @RequestMapping("/load/{id}")
    public User loadDetail(@PathVariable Long id) {
        User user = this.oAuth2RestTemplate.getForEntity("http://localhost:2100/users/{id}",User.class,id).getBody();
        return user;
    }
    @RequestMapping("/{id}/comments")
    public List<ProductComment> comments(@PathVariable Long id) {
        ProductComment productComment = new ProductComment(123l,123l,"test",servicePort);
        List<ProductComment> list = Lists.newArrayList(productComment);
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        return list.stream().map((c)->{
            return new ProductComment(123l,123l,"test",servicePort);
        }).collect(Collectors.toList());
        //return Lists.newArrayList(productComment);
    }
    @RequestMapping("/LoadUser/{userId}")
    @HystrixCommand(fallbackMethod = "findAllFallback",commandProperties=@HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"))
    public User LoadUser(@PathVariable Long userId) {
        User user1 = new User();
        user1.setId(123l);
        user1.setAddress("fall");
        user1.setName("fall");
        logger.info("==============================="+user1);
        //User user = this.restTemplate.getForEntity("http://localhost:2100/users/{id}",User.class,userId).getBody();
        //User user = this.restTemplate.getForEntity("http://userservice/users/{id}",User.class,userId).getBody();
        User user = userService.load(userId);
        if (user != null) {
            return user;
        }
        return null;
    }

    public User findAllFallback(@PathVariable Long userId) {
        User user = new User();
        user.setId(123l);
        user.setAddress("fall");
        user.setName("fall");
        List<User> list = Lists.newArrayList(user);
        return user;
    }
}
