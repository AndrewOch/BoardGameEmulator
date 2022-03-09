package ru.kpfu.itis.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.kpfu.itis.dto.CreateGameDto;
import ru.kpfu.itis.dto.EditGameDto;
import ru.kpfu.itis.dto.GameDto;
import ru.kpfu.itis.dto.PlayDto;
import ru.kpfu.itis.models.entities.*;
import ru.kpfu.itis.models.entities.Currency;
import ru.kpfu.itis.models.form.CardForm;
import ru.kpfu.itis.models.form.CurrencyForm;
import ru.kpfu.itis.models.form.DeckForm;
import ru.kpfu.itis.models.form.GameForm;
import ru.kpfu.itis.services.CookieService;
import ru.kpfu.itis.services.interfaces.GamesService;
import ru.kpfu.itis.services.interfaces.UsersService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
public class GamesController {

    @Autowired
    @Qualifier(value = "gamesService")
    private GamesService gamesService;
    @Autowired
    private UsersService usersService;

    private ArrayList<PlayDeckPair> decks;

    @RequestMapping(method = RequestMethod.GET, value = "/games")
    private ModelAndView gamesPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ModelAndView modelAndView = new ModelAndView();

        Cookie auth = CookieService.getCookie(request, "auth");
        System.out.println("cookie value: " + auth.getValue());
        if (auth != null) {
            User user = usersService.findUserByCookieValue(auth.getValue());
            List<Game> games = gamesService.findGamesByUserId(user.getId());
            modelAndView.addObject("games", games);

            String currentEditGameId = request.getParameter("current_edit_game_id");
            if (currentEditGameId != null) {
                request.getRequestDispatcher("WEB-INF/jsp/creator.jsp").forward(request, response);
                return null;
            }

            String currentPlayGameId = request.getParameter("current_play_game_id");
            if (currentPlayGameId != null) {
                request.getRequestDispatcher("WEB-INF/jsp/play.jsp").forward(request, response);
                return null;
            }
        }
        modelAndView.setViewName("games");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/games")
    private String createGame(GameDto gameDto, HttpServletRequest request, HttpServletResponse response) {

        String name = gameDto.getGame_name();
        String description = gameDto.getGame_description();

        if (!name.equals("") && !description.equals("")) {
            GameForm gameForm = new GameForm(name, description);
            Game game = gamesService.addGame(gameForm);
            if (game != null) {
                gamesService.linkGameToUser(game.getId(), usersService.findUserByCookieValue(Objects.requireNonNull(CookieService.getCookie(request, "auth")).getValue()).getId());
                Cookie cookie = new Cookie("current_edit_game_id", game.getId().toString());
                response.addCookie(cookie);
                return "redirect:/creator";
            }
        }
        return "redirect:/games";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/creator")
    private ModelAndView createGamePage(CreateGameDto createGameDto, HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        Cookie auth = CookieService.getCookie(request, "auth");
        if (auth != null) {

            Cookie editingGameId = CookieService.getCookie(request, "editing_game");
            Cookie deckOfAddingCard = CookieService.getCookie(request, "deck_of_adding_card");

            String gameId = createGameDto.getChoose_game();
            String gameName = createGameDto.getGame_name();
            String gameDescription = createGameDto.getGame_description();
            if (gameId != null && gameName != null && gameDescription != null) {
                Game game = gamesService.updateGameInfoById(Long.valueOf(gameId), gameName, gameDescription);
            }

            String currencyId = createGameDto.getChoose_currency();
            String currencyName = createGameDto.getCurrency_name();
            String currencyDescription = createGameDto.getCurrency_description();
            if (currencyId != null && currencyName != null && currencyDescription != null) {
                if (currencyId.equals("create")) {
                    assert editingGameId != null;
                    CurrencyForm currencyForm = new CurrencyForm(currencyName, currencyDescription, Long.valueOf(editingGameId.getValue()));
                    Currency currency = gamesService.addCurrency(currencyForm);
                } else {
                    Currency currency = gamesService.updateCurrencyInfoById(Long.valueOf(currencyId), currencyName, currencyDescription);
                }
            }

            String deckId = createGameDto.getChoose_deck();
            String deckName = createGameDto.getDeck_name();
            String deckDescription = createGameDto.getDeck_description();
            if (deckId != null && deckName != null && deckDescription != null) {
                if (deckId.equals("create")) {
                    DeckForm deckForm = new DeckForm(deckName, deckDescription);
                    assert editingGameId != null;
                    Deck deck = gamesService.addDeck(deckForm, Long.valueOf(editingGameId.getValue()));
                } else {
                    Deck deck = gamesService.updateDeckInfoById(Long.valueOf(deckId), deckName, deckDescription);
                }
            }

            String cardId = createGameDto.getChoose_card();
            String cardName = createGameDto.getCard_name();
            String cardDescription = createGameDto.getCard_description();
            String cardCurrencyId = createGameDto.getCurrency_of_a_card();
            String value = createGameDto.getCard_value();
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
            modelAndView.addObject("games", games);
        }

        modelAndView.setViewName("creator");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/creator")
    private String editGame(EditGameDto editGameDto, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        ObjectMapper objectMapper = new ObjectMapper();
        Cookie auth = CookieService.getCookie(request, "auth");
        if (auth != null) {

            String param = editGameDto.getCurrent_edit_game_id();
            if (param != null) {
                Cookie cookie = new Cookie("editing_game", param);
                response.addCookie(cookie);
                Game game = gamesService.findGameById(Long.valueOf(param));
                String json = objectMapper.writeValueAsString(game);
                System.out.println(json);
                response.setContentType("application/json");
                response.getWriter().println(json);
            }
            param = editGameDto.getCurrent_edit_currency_id();
            if (param != null) {
                Currency currency = gamesService.findCurrencyById(Long.valueOf(param));
                String json = objectMapper.writeValueAsString(currency);
                response.setContentType("application/json");
                response.getWriter().println(json);
            }
            param = editGameDto.getCurrent_edit_deck_id();
            if (param != null) {
                Deck currency = gamesService.findDeckById(Long.valueOf(param));
                String json = objectMapper.writeValueAsString(currency);
                response.setContentType("application/json");
                System.out.println(json);
                response.getWriter().println(json);
            }

            param = editGameDto.getDeck_to_show_cards();
            if (param != null) {
                Cookie cookie = new Cookie("deck_of_adding_card", param);
                response.addCookie(cookie);
                Deck currency = gamesService.findDeckById(Long.valueOf(param));
                String json = objectMapper.writeValueAsString(currency);
                response.setContentType("application/json");
                response.getWriter().println(json);
            }
            param = editGameDto.getCurrent_edit_card_id();
            if (param != null) {
                Card card = gamesService.findCardById(Long.valueOf(param));
                String json = objectMapper.writeValueAsString(card);
                response.setContentType("application/json");
                response.getWriter().println(json);
            }
            request.getRequestDispatcher("WEB-INF/jsp/creator.jsp").forward(request, response);
            return null;
        }
        return "redirect:/create";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/play")
    private ModelAndView playGamePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("play");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/play")
    private void playGame(PlayDto playDto, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Cookie auth = CookieService.getCookie(request, "auth");
        ObjectMapper objectMapper = new ObjectMapper();
        if (auth != null) {

            String param = playDto.getCurrent_play_game_id();
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
                response.setContentType("application/json");
                response.getWriter().println(json);
            }

            param = playDto.getDeck_to_take_card_from();
            if (param != null) {
                Long id = Long.valueOf(param);
                takeCard(id);
                String json = objectMapper.writeValueAsString(getDeckById(id));
                response.setContentType("application/json");
                response.getWriter().println(json);
            }

            param = playDto.getDeck_to_shuffle();
            if (param != null) {
                Long id = Long.valueOf(param);
                shuffleDeck(id);
                String json = objectMapper.writeValueAsString(getDeckById(id));
                response.setContentType("application/json");
                response.getWriter().println(json);
            }
        }
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

}
