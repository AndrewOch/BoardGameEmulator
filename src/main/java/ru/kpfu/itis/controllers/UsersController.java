package ru.kpfu.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.kpfu.itis.dto.AuthDto;
import ru.kpfu.itis.dto.SignUpDto;
import ru.kpfu.itis.models.entities.User;
import ru.kpfu.itis.models.form.UserForm;
import ru.kpfu.itis.services.interfaces.UsersService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(method = RequestMethod.GET, value = "/auth")
    private ModelAndView authorisationPage(String redirect) {
        ModelAndView modelAndView = new ModelAndView();
        if (redirect != null) {
            modelAndView.setViewName("redirect:/" + redirect);
            return modelAndView;
        }
        modelAndView.setViewName("authorisation");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/auth")
    private ModelAndView authorise(AuthDto authDto, HttpServletResponse response) {
        Cookie cookie = usersService.signIn(authDto);

        ModelAndView modelAndView = new ModelAndView();
        if (cookie != null) {
            response.addCookie(cookie);
            response.addCookie(new Cookie("username", authDto.getLogin()));
            modelAndView.setViewName("redirect:/games");
            return modelAndView;
        } else {
            modelAndView.addObject("signInStatus", "Неправильный логин или пароль");
            modelAndView.setViewName("redirect:/auth");
            return modelAndView;
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    private ModelAndView signUpPage(String redirect) {
        ModelAndView modelAndView = new ModelAndView();
        if (redirect != null) {
            modelAndView.setViewName("redirect:/" + redirect);
            return modelAndView;
        }
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    private ModelAndView signUp(SignUpDto signUpDto, HttpServletResponse response) {

        ModelAndView modelAndView = new ModelAndView();
        System.out.println(signUpDto.toString());

        String username = signUpDto.getUsername();
        String email = signUpDto.getEmail();
        String password = signUpDto.getPassword();
        String retypePassword = signUpDto.getRetype_password();
        String status;
        if (username.length() < 4) {
            status = "Длина ника - не менее 4 символов!";
            modelAndView.addObject("validation", status);
            modelAndView.setViewName("redirect:/signUp");
            return modelAndView;
        }
        if (email.length() == 0) {
            status = "Поле почты не должно быть пустым!";
            modelAndView.addObject("validation", status);
            modelAndView.setViewName("redirect:/signUp");
            return modelAndView;
        }
        if (password.length() < 8) {
            status = "Длина пароля - не менее 8 символов!";
            modelAndView.addObject("validation", status);
            modelAndView.setViewName("redirect:/signUp");
            return modelAndView;
        }
        if (password.equals(retypePassword)) {

            UserForm userForm = new UserForm();
            userForm.setUsername(username);
            userForm.setEmail(email);
            userForm.setPassword(password);

            User user = usersService.register(userForm);
            if (user != null) {
                AuthDto authDto = new AuthDto().builder()
                        .login(user.getUsername())
                        .password(password)
                        .build();

                Cookie cookie = usersService.signIn(authDto);
                cookie.setMaxAge(10 * 60 * 60);

                response.addCookie(cookie);
                response.addCookie(new Cookie("username", username));
                modelAndView.setViewName("redirect:/games");
                return modelAndView;
            } else {
                status = "Не удалось создать аккаунт!";
            }
        } else {
            status = "Пароли не совпадают!";
        }
        modelAndView.addObject("validation", status);
        modelAndView.setViewName("redirect:/signUp");
        return modelAndView;
    }
}
