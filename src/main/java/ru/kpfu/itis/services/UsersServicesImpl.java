package ru.kpfu.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.dto.AuthDto;
import ru.kpfu.itis.models.form.UserForm;
import ru.kpfu.itis.models.entities.Auth;
import ru.kpfu.itis.models.entities.User;
import ru.kpfu.itis.repositories.interfaces.AuthRepository;
import ru.kpfu.itis.repositories.interfaces.UsersRepository;
import ru.kpfu.itis.services.interfaces.UsersService;

import javax.servlet.http.Cookie;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersServicesImpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(UserForm userForm) {

        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setEmail(userForm.getEmail());

        String passwordHash = passwordEncoder.encode(userForm.getPassword());
        user.setPasswordHash(passwordHash);
        return usersRepository.save(user);
    }

    @Override
    public Cookie signIn(AuthDto authDto) {

        User user = null;
        Optional<User> optional = usersRepository.findByLogin(authDto.getLogin());
        if (optional.isPresent()) user = optional.get();

        System.out.println(user);
        if (user != null) {
            if (passwordEncoder.matches(authDto.getPassword(), user.getPasswordHash())) {
                System.out.println("Вход выполнен!");
                String cookieValue = UUID.randomUUID().toString();

                Auth auth = new Auth();
                auth.setUser(user);
                auth.setCookieValue(cookieValue);
                authRepository.save(auth);

                Cookie cookie = new Cookie("auth", cookieValue);
                cookie.setMaxAge(10 * 60 * 60);
                return cookie;
            } else {
                System.out.println("Вход не выполнен!");
            }
        }

        return null;
    }

    @Override
    public User findUserByCookieValue(String cookieValue) {
        Optional<Auth> optional = authRepository.findByCookieValue(cookieValue);

        Auth auth = null;
        if (optional.isPresent()) {
            auth = optional.get();
        }
        if (auth != null) {
            return auth.getUser();
        } else {
            return null;
        }
    }
}