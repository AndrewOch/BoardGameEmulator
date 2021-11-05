package ru.kpfu.itis.servlets;

import ru.kpfu.itis.form.GameForm;
import ru.kpfu.itis.model.Game;
import ru.kpfu.itis.model.User;
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
import java.util.List;

@WebServlet("/games")
public class GamesServlet extends HttpServlet {

    private GamesService gamesService;
    private UsersService usersService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        gamesService = (GamesService) config.getServletContext().getAttribute("gamesService");
        usersService = (UsersService) config.getServletContext().getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cookie auth = CookieService.getCookie(req, "auth");
        if (auth != null) {
            User user = usersService.findUserByCookieValue(auth.getValue());
            List<Game> games = gamesService.findGamesByUserId(user.getId());
            req.setAttribute("games", games);

            String currentEditGameId = req.getParameter("current_edit_game_id");
            if (currentEditGameId != null) {
                req.getRequestDispatcher("WEB-INF/jsp/creator.jsp").forward(req, resp);
                return;
            }
        }
        req.getRequestDispatcher("WEB-INF/jsp/games.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("game-name");
        String description = req.getParameter("game-description");

        if (!name.equals("") && !description.equals("")) {
            GameForm gameForm = new GameForm(name, description);
            Game game = gamesService.addGame(gameForm);
            if (game != null) {
                gamesService.linkGameToUser(game.getId(), usersService.findUserByCookieValue(CookieService.getCookie(req, "auth").getValue()).getId());
                Cookie cookie = new Cookie("current_edit_game_id", game.getId().toString());
                resp.addCookie(cookie);
                req.getRequestDispatcher("WEB-INF/jsp/creator.jsp").forward(req, resp);
                return;
            }
        }
        req.getRequestDispatcher("WEB-INF/jsp/games.jsp").forward(req, resp);
    }
}
