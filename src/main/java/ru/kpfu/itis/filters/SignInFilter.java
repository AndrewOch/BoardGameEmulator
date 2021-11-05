package ru.kpfu.itis.filters;

import ru.kpfu.itis.services.CookieService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(value = {"/main", "/play", "/games", "/creator"})
public class SignInFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Cookie cookie = CookieService.getCookie(request, "auth");

        if (cookie != null) {
            System.out.println(cookie.getValue());
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            System.out.println("Пользователь не аутентифицирован!!!");
            response.sendRedirect("/auth");
        }
    }

    @Override
    public void destroy() {

    }
}