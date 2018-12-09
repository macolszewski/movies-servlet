package model;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/actor","/director"})
//@WebFilter(urlPatterns = "/main")
public class LoginFilter implements Filter {

    @Inject
    private UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = servletRequest.getParameter("token");
        if (userService.getUserByToken(token)!=null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse)servletResponse).setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}