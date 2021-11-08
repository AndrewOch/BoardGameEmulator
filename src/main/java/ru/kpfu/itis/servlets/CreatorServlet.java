package ru.kpfu.itis.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kpfu.itis.form.CardForm;
import ru.kpfu.itis.form.CurrencyForm;
import ru.kpfu.itis.form.DeckForm;
import ru.kpfu.itis.model.*;
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

            String param = req.getParameter("current_edit_game_id");

            Cookie editingGameId = CookieService.getCookie(req, "editing_game");
            Cookie deckOfAddingCard = CookieService.getCookie(req, "deck_of_adding_card");

            String gameId = req.getParameter("choose-game");
            String gameName = req.getParameter("game-name");
            String gameDescription = req.getParameter("game-description");
            if (gameId != null && gameName != null && gameDescription != null) {
                Game game = gamesService.updateGameInfoById(Long.valueOf(gameId), gameName, gameDescription);
            }

            String currencyId = req.getParameter("choose-currency");
            String currencyName = req.getParameter("currency-name");
            String currencyDescription = req.getParameter("currency-description");
            if (currencyId != null && currencyName != null && currencyDescription != null) {
                if (currencyId.equals("create")) {
                    assert editingGameId != null;
                    CurrencyForm currencyForm = new CurrencyForm(currencyName, currencyDescription, Long.valueOf(editingGameId.getValue()));
                    Currency currency = gamesService.addCurrency(currencyForm);
                } else {
                    Currency currency = gamesService.updateCurrencyInfoById(Long.valueOf(currencyId), currencyName, currencyDescription);
                }
            }

            String deckId = req.getParameter("choose-deck");
            String deckName = req.getParameter("deck-name");
            String deckDescription = req.getParameter("deck-description");
            if (deckId != null && deckName != null && deckDescription != null) {
                if (deckId.equals("create")) {
                    DeckForm deckForm = new DeckForm(deckName, deckDescription);
                    assert editingGameId != null;
                    Deck deck = gamesService.addDeck(deckForm, Long.valueOf(editingGameId.getValue()));
                } else {
                    Deck deck = gamesService.updateDeckInfoById(Long.valueOf(deckId), deckName, deckDescription);
                }
            }

            String cardId = req.getParameter("choose-card");
            String cardName = req.getParameter("card-name");
            String cardDescription = req.getParameter("card-description");
            String cardCurrencyId = req.getParameter("currency-of-a-card");
            String value = req.getParameter("card-value");

            if (cardId != null && cardName != null && cardDescription != null
                    && cardCurrencyId != null && value != null) {
                if (cardId.equals("create")) {
                    assert deckOfAddingCard != null;
                    CardForm cardForm = new CardForm(cardName, cardDescription, Long.valueOf(cardCurrencyId), Integer.valueOf(value), Long.valueOf(deckOfAddingCard.getValue()));
                    Card card = gamesService.addCard(cardForm);
                } else {
                    Card card = gamesService.updateCardInfoById(Long.valueOf(cardId), cardName, cardDescription, Long.valueOf(cardCurrencyId), Integer.valueOf(value));
                }
            }

            User user = usersService.findUserByCookieValue(auth.getValue());
            List<Game> games = gamesService.findGamesByUserId(user.getId());
            req.setAttribute("games", games);
            req.getRequestDispatcher("WEB-INF/jsp/creator.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Cookie auth = CookieService.getCookie(req, "auth");
        if (auth != null) {

            String param = req.getParameter("current_edit_game_id");
            if (param != null) {
                Cookie cookie = new Cookie("editing_game", param);
                resp.addCookie(cookie);
                Game game = gamesService.findGameById(Long.valueOf(param));
                String json = objectMapper.writeValueAsString(game);
                System.out.println(json);
                resp.setContentType("application/json");
                resp.getWriter().println(json);
            }
            param = req.getParameter("current_edit_currency_id");
            if (param != null) {
                Currency currency = gamesService.findCurrencyById(Long.valueOf(param));
                String json = objectMapper.writeValueAsString(currency);
                resp.setContentType("application/json");
                resp.getWriter().println(json);
            }
            param = req.getParameter("current_edit_deck_id");
            if (param != null) {
                Deck currency = gamesService.findDeckById(Long.valueOf(param));
                String json = objectMapper.writeValueAsString(currency);
                resp.setContentType("application/json");
                System.out.println(json);
                resp.getWriter().println(json);
            }

            param = req.getParameter("deck_to_show_cards");
            if (param != null) {
                Cookie cookie = new Cookie("deck_of_adding_card", param);
                resp.addCookie(cookie);
                Deck currency = gamesService.findDeckById(Long.valueOf(param));
                String json = objectMapper.writeValueAsString(currency);
                resp.setContentType("application/json");
                resp.getWriter().println(json);
            }
            param = req.getParameter("current_edit_card_id");
            if (param != null) {
                Card card = gamesService.findCardById(Long.valueOf(param));
                String json = objectMapper.writeValueAsString(card);
                resp.setContentType("application/json");
                resp.getWriter().println(json);
            }
        }
    }
}
