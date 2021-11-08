package ru.kpfu.itis.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.model.Deck;
import ru.kpfu.itis.model.Game;
import ru.kpfu.itis.model.PlayDeckPair;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

@WebServlet("/play")
public class PlayServlet extends HttpServlet {

    private GamesService gamesService;
    private UsersService usersService;
    private ArrayList<PlayDeckPair> decks;

    @Override
    public void init(ServletConfig config) throws ServletException {
        gamesService = (GamesService) config.getServletContext().getAttribute("gamesService");
        usersService = (UsersService) config.getServletContext().getAttribute("usersService");
    }

    private PlayDeckPair getDeckById(Long deckId) {
        for (PlayDeckPair deck : decks) {
            if (deck.getDeckId().equals(deckId)) {
                return deck;
            }
        }
        return null;
    }

    private void takeCard(Long deckId) {
        PlayDeckPair deck = getDeckById(deckId);
        assert deck != null;
        Card card = deck.getDeck().pop();
        deck.getWaste().push(card);
    }

    private void shuffleDeck(Long deckId) {
        PlayDeckPair deck = getDeckById(deckId);
        assert deck != null;

        Stack<Card> cardsFromWaste = deck.getWaste();

        Stack<Card> cardsFromDeck = deck.getDeck();

        Stack<Card> cards = new Stack<>();
        cards.addAll(cardsFromWaste);
        cards.addAll(cardsFromDeck);

        deck.getWaste().clear();

        Collections.shuffle(cards);

        deck.setDeck(cards);
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
                decks = new ArrayList<>();
                for (Deck deck : game.getDecks()) {
                    Stack<Card> stack = new Stack<>();
                    stack.addAll(deck.getCards());
                    PlayDeckPair playDeckPair = new PlayDeckPair(deck.getId(), stack);
                    decks.add(playDeckPair);
                }
                String json = objectMapper.writeValueAsString(game);
                resp.setContentType("application/json");
                resp.getWriter().println(json);
            }

            param = req.getParameter("deck_to_take_card_from");
            if (param != null) {
                Long id = Long.valueOf(param);
                takeCard(id);
                String json = objectMapper.writeValueAsString(getDeckById(id));
                resp.setContentType("application/json");
                resp.getWriter().println(json);
            }

            param = req.getParameter("deck_to_shuffle");
            if (param != null) {
                Long id = Long.valueOf(param);
                shuffleDeck(id);
                String json = objectMapper.writeValueAsString(getDeckById(id));
                resp.setContentType("application/json");
                resp.getWriter().println(json);
            }
        }
    }
}
