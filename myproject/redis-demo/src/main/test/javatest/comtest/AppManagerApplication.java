package javatest.comtest;

        import org.mybatis.spring.annotation.MapperScan;
        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by hsuyung on 2019/1/4.
 */
@MapperScan(basePackages = "com.business.mapper")
@SpringBootApplication
public class AppManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppManagerApplication.class, args);
    }
}
