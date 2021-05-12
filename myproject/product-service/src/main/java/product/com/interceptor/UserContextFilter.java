package product.com.interceptor;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@Component
public class UserContextFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        ThreadLocal<String> tokenThread = new ThreadLocal<String>();
        tokenThread.set(request1.getHeader(UserContext.AUTH_TOKEN));
        UserContext.setAuthToken(tokenThread);
        chain.doFilter(request1,response);
    }

    @Override
    public void destroy() {

    }
}
