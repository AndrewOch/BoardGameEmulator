package ru.kpfu.itis.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kpfu.itis.model.Game;
import ru.kpfu.itis.services.CookieService;
import ru.kpfu.itis.services.GamesService;
import ru.kpfu.itis.services.UsersService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/play")
public class PlayServlet extends HttpServlet {

    private GamesService gamesService;
    private UsersService usersService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        gamesService = (GamesService) config.getServletContext().getAttribute("gamesService");
        usersService = (UsersService) config.getServletContext().getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        String currentEditGameId = req.getParameter("current_edit_game_id");

        try {
            req.getRequestDispatcher("WEB-INF/jsp/play.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie auth = CookieService.getCookie(req, "auth");
        ObjectMapper objectMapper = new ObjectMapper();
        if (auth != null) {

            String param = req.getParameter("current_play_game_id");
            if (param != null) {
                Game game = gamesService.findGameById(Long.valueOf(param));
                String json = objectMapper.writeValueAsString(game);
                resp.setContentType("application/json");
                resp.getWriter().println(json);
            }
        }
    }
}
