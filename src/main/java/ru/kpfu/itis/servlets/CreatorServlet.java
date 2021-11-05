package ru.kpfu.itis.servlets;

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

@WebServlet("/creator")
public class CreatorServlet extends HttpServlet {

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

//            String currentEditGameId = req.getParameter("current_edit_game_id");
//            if (currentEditGameId != null) {
//                System.out.println(currentEditGameId);
//                Game game = gamesService.findGameById(Long.valueOf(currentEditGameId));
//                req.setAttribute("current_game", game);
//            }
        }
        req.getRequestDispatcher("WEB-INF/jsp/creator.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie auth = CookieService.getCookie(req, "auth");
        if (auth != null) {

            String currentEditGameId = req.getParameter("current_edit_game_id");
            if (currentEditGameId != null) {
                System.out.println(currentEditGameId);
                Game game = gamesService.findGameById(Long.valueOf(currentEditGameId));
                req.setAttribute("current_game", game);
            }
        }
        req.getRequestDispatcher("WEB-INF/jsp/creator.jsp").forward(req, resp);
    }
}
