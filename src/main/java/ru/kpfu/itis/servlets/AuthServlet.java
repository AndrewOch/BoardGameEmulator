package ru.kpfu.itis.servlets;

import ru.kpfu.itis.form.LoginForm;
import ru.kpfu.itis.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

    private UsersService usersService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        usersService = (UsersService) config.getServletContext().getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String registerButton = req.getParameter("register-button");
        if (registerButton != null) {
            req.getRequestDispatcher("WEB-INF/jsp/register.jsp").forward(req, resp);
            return;
        }

        req.getRequestDispatcher("WEB-INF/jsp/authorisation.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("login");
        String password = request.getParameter("password");

        LoginForm loginForm = new LoginForm(username, password);
        Cookie cookie = usersService.signIn(loginForm);

        if (cookie != null) {
            response.addCookie(cookie);
            response.addCookie(new Cookie("username", username));
            response.addCookie(cookie);

            response.sendRedirect("/games");
        } else {
            request.setAttribute("signInStatus", "Неправильный логин или пароль");
            request.getRequestDispatcher("WEB-INF/jsp/authorisation.jsp").forward(request, response);
        }
    }
}
