package product.com.interceptor;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class UserContextFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        ThreadLocal<String> tokenThread = new ThreadLocal<String>();
        String token = request1.getHeader(UserContext.AUTH_TOKEN).replace("bearer ","");
        //解析JWT
        Claims claims = Jwts.parser().setSigningKey("secret".getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token).getBody();
        tokenThread.set(request1.getHeader(UserContext.AUTH_TOKEN));
        UserContext.setAuthToken(request1.getHeader(UserContext.AUTH_TOKEN));
        chain.doFilter(request1,response);
    }

    @Override
    public void destroy() {

    }
}
