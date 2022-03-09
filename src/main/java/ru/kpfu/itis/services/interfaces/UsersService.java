package ru.kpfu.itis.services.interfaces;

import ru.kpfu.itis.dto.AuthDto;
import ru.kpfu.itis.models.form.UserForm;
import ru.kpfu.itis.models.entities.User;

import javax.servlet.http.Cookie;

public interface UsersService {
    User register(UserForm userForm);

    Cookie signIn(AuthDto authDto);

    User findUserByCookieValue(String cookieValue);

}