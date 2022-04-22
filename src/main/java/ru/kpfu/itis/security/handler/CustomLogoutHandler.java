package ru.kpfu.itis.security.handler;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.models.entities.Auth;
import ru.kpfu.itis.repositories.AuthRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("customLogoutHandler")
public class CustomLogoutHandler implements LogoutHandler {

    @Autowired
    private AuthRepository authRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals("Auth")){
                Auth value = authRepository.findByCookieValue(cookie.getValue()).get();
                authRepository.delete(value);
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
            }
        }
    }
}
