package ru.kpfu.itis.servlets;

import ru.kpfu.itis.form.UserForm;
import ru.kpfu.itis.model.User;
import ru.kpfu.itis.repositories.UsersRepository;
import ru.kpfu.itis.repositories.UsersRepositoryImpl;
import ru.kpfu.itis.services.UsersService;
import ru.kpfu.itis.services.UsersServicesImpl;

import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {

    private UsersService usersService;

    @Override
    public void init() throws ServletException {
        try {
            //Подгружаем драйвер, оно автоматически регистрирует JDBC
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/board-game-emulator-db", "postgres", "12345678");

            UsersRepository usersRepository = new UsersRepositoryImpl(connection);
            usersService = new UsersServicesImpl(usersRepository);
        } catch (SQLException e) {
            System.out.println("Unavailable");
            throw new UnavailableException("Сайт недоступен!!!");
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
            throw new UnavailableException("Сайт недоступен!!!");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("jsp/registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("login");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String retypePassword = request.getParameter("retype_password");
        String status = "";
        if (username.length() < 4) {
            status = "Длина ника - не менее 4 символов!";
            request.setAttribute("validation", status);
            request.getRequestDispatcher("jsp/registration.jsp").forward(request, response);
            return;
        }
        if (email.length() == 0) {
            status = "Поле почты не должно быть пустым!";
            request.setAttribute("validation", status);
            request.getRequestDispatcher("jsp/registration.jsp").forward(request, response);
            return;
        }
        if (password.length() < 8) {
            status = "Длина пароля - не менее 8 символов!";
            request.setAttribute("validation", status);
            request.getRequestDispatcher("jsp/registration.jsp").forward(request, response);
            return;
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
                response.sendRedirect("games");
                return;
            } else {
                status = "Не удалось создать аккаунт!";
            }
        } else {
            status = "Пароли не совпадают!";
        }

        request.setAttribute("validation", status);
        request.getRequestDispatcher("jsp/registration.jsp").forward(request, response);
    }
}
