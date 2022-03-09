package ru.kpfu.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.kpfu.itis.dto.AuthDto;
import ru.kpfu.itis.dto.SignUpDto;
import ru.kpfu.itis.models.form.UserForm;
import ru.kpfu.itis.models.entities.User;
import ru.kpfu.itis.services.interfaces.UsersService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(method = RequestMethod.GET, value = "/auth")
    private ModelAndView authorisationPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("authorisation");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/auth")
    private String authorise(AuthDto authDto, HttpServletResponse response) {
        Cookie cookie = usersService.signIn(authDto);

        if (cookie != null) {
            response.addCookie(cookie);
            response.addCookie(new Cookie("username", authDto.getLogin()));
            return "redirect:/games";
        } else {
            //TODO Оповещение о неправильном пароле
            //request.setAttribute("signInStatus", "Неправильный логин или пароль");
            //request.getRequestDispatcher("WEB-INF/jsp/authorisation.jsp").forward(request, response);
            return "redirect:/auth";
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    private ModelAndView signUpPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    private String signUp(SignUpDto signUpDto, HttpServletResponse response) {

        System.out.println(signUpDto.toString());

        String username = signUpDto.getUsername();
        String email = signUpDto.getEmail();
        String password = signUpDto.getPassword();
        String retypePassword = signUpDto.getRetype_password();
        String status = "";
        if (username.length() < 4) {
            //TODO
//            status = "Длина ника - не менее 4 символов!";
//            request.setAttribute("validation", status);
//            request.getRequestDispatcher("WEB-INF/jsp/register.jsp").forward(request, response);
            return "redirect:/signUp";
        }
        if (email.length() == 0) {
            //TODO
//            status = "Поле почты не должно быть пустым!";
//            request.setAttribute("validation", status);
//            request.getRequestDispatcher("WEB-INF/jsp/register.jsp").forward(request, response);
            return "redirect:/signUp";
        }
        if (password.length() < 8) {
            //TODO
//            status = "Длина пароля - не менее 8 символов!";
//            request.setAttribute("validation", status);
//            request.getRequestDispatcher("WEB-INF/jsp/register.jsp").forward(request, response);
            return "redirect:/signUp";
        }
        if (password.equals(retypePassword)) {

            UserForm userForm = new UserForm();
            userForm.setUsername(username);
            userForm.setEmail(email);
            userForm.setPassword(password);

            User user = usersService.register(userForm);
            if (user != null) {

                String cookieValue = UUID.randomUUID().toString();
                System.out.println(cookieValue);
                Cookie cookie = new Cookie("auth", cookieValue);
                cookie.setMaxAge(10 * 60 * 60);

                response.addCookie(cookie);
                response.addCookie(new Cookie("username", username));
                //request.getRequestDispatcher("WEB-INF/jsp/games.jsp").forward(request, response);
                return "redirect:/games";
            } else {
                status = "Не удалось создать аккаунт!";
            }
        } else {
            status = "Пароли не совпадают!";
        }

        //TODO
//        request.setAttribute("validation", status);
//        request.getRequestDispatcher("WEB-INF/jsp/register.jsp").forward(request, response);
        return "redirect:/signUp";
    }
}

