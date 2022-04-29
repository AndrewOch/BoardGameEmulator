package ru.kpfu.itis.services.interfaces;

import ru.kpfu.itis.models.dtos.AuthDto;
import ru.kpfu.itis.models.forms.AuthForm;
import ru.kpfu.itis.models.forms.UserForm;
import ru.kpfu.itis.models.entities.User;

import javax.servlet.http.Cookie;

public interface UsersService {
    User register(UserForm userForm);

    Cookie signIn(AuthForm authDto);

    User findUserByCookieValue(String cookieValue);

    boolean usernameDoesntExist(String username);
}