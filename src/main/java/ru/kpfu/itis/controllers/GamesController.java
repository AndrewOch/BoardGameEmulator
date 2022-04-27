package ru.kpfu.itis.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.kpfu.itis.models.dtos.EditGameDto;
import ru.kpfu.itis.models.dtos.GameDto;
import ru.kpfu.itis.models.dtos.PlayDto;
import ru.kpfu.itis.models.entities.*;
import ru.kpfu.itis.models.entities.Currency;
import ru.kpfu.itis.models.forms.CardForm;
import ru.kpfu.itis.models.forms.CurrencyForm;
import ru.kpfu.itis.models.forms.DeckForm;
import ru.kpfu.itis.models.forms.GameForm;
import ru.kpfu.itis.services.EditGameService;
import ru.kpfu.itis.services.interfaces.GamesService;
import ru.kpfu.itis.services.interfaces.UsersService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@Controller
public class GamesController {

    @Autowired
    @Qualifier(value = "gamesService")
    private GamesService gamesService;

    @Autowired
    private EditGameService editGameService;

    private ArrayList<PlayDeckPair> decks;

    @RequestMapping(method = RequestMethod.GET, value = "/games")
    private ModelAndView gamesPage(Authentication authentication, GameDto gameDto) throws ServletException, IOException {
        ModelAndView modelAndView = new ModelAndView();

        if (authentication == null) {
            modelAndView.setViewName("redirect:/auth");
            return modelAndView;
        }
        User user = (User) authentication.getPrincipal();
        modelAndView.addObject("username", user.getUsername());

        List<Game> games = gamesService.findGamesByUserId(user.getId());
        modelAndView.addObject("games", games);

        String currentEditGameId = gameDto.getCurrentEditGameId();
        if (currentEditGameId != null) {
            modelAndView.addObject("currentEditGameId", Long.valueOf(currentEditGameId));
            modelAndView.setViewName("redirect:/creator");
            return modelAndView;
        }

        String currentPlayGameId = gameDto.getCurrentPlayGameId();
        if (currentPlayGameId != null) {
            modelAndView.addObject("currentPlayGameId", Long.valueOf(currentPlayGameId));
            modelAndView.setViewName("redirect:/play");
            return modelAndView;
        }

        modelAndView.setViewName("games");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/games")
    private ModelAndView createGame(GameDto gameDto, Authentication authentication) {
        if (authentication == null) {
            return new ModelAndView("redirect:/auth");
        }
        User user = (User) authentication.getPrincipal();

        String name = gameDto.getGameName();
        String description = gameDto.getGameDescription();

        if (!name.equals("") && !description.equals("")) {
            GameForm gameForm = new GameForm(name, description, user);
            Game game = gamesService.addGame(gameForm);
            if (game != null) {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.addObject("currentEditGameId", game.getId());
                modelAndView.setViewName("redirect:/creator");
                return modelAndView;
            }
        }
        return new ModelAndView("redirect:/games");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/creator")
    private ModelAndView editGamePage(Authentication authentication, EditGameDto editGameDto) {
        ModelAndView modelAndView = new ModelAndView();
        if (authentication == null) {
            modelAndView.setViewName("redirect:/auth");
            return modelAndView;
        }
        User user = (User) authentication.getPrincipal();
        String string = editGameDto.getCurrentEditGameId();
        Long currentEditGameId;
        if (string != null) {
            currentEditGameId = Long.valueOf(string);
            modelAndView.addObject("currentEditGameId", currentEditGameId);
            Game game = gamesService.findGameById(currentEditGameId);
            modelAndView.addObject(game);
        }
        List<Game> games = gamesService.findGamesByUserId(user.getId());
        modelAndView.addObject("games", games);
        modelAndView.setViewName("creator");
        return modelAndView;
    }

    @PostMapping("/creator")
    private ModelAndView editGame(EditGameDto editGameDto, Authentication authentication, HttpServletResponse response, MultipartFile playGroundFile) throws IOException, ServletException {
        ModelAndView modelAndView = new ModelAndView();
        ObjectMapper objectMapper = new ObjectMapper();
        if (authentication == null) {
            modelAndView.setViewName("redirect:/auth");
            return modelAndView;
        }
        User user = (User) authentication.getPrincipal();
        List<Game> games = gamesService.findGamesByUserId(user.getId());
        modelAndView.addObject(games);

        String editGameId = editGameDto.getCurrentEditGameId();
        Long currentEditGameId;
        if (editGameId != null && !editGameId.equals("false")) {
            currentEditGameId = Long.valueOf(editGameId);
            modelAndView.addObject("currentEditGameId", currentEditGameId);
        } else {
            return null;
        }
        String json = editGameService.editGame(editGameDto);
        System.out.println(json);
        response.setContentType("application/json");
        response.getWriter().println(json);
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/play")
    private ModelAndView playGamePage(Long currentPlayGameId) {
        ModelAndView modelAndView = new ModelAndView();
        if (currentPlayGameId == null) {
            modelAndView.setViewName("redirect:/games");
            return modelAndView;
        }
        Game game = gamesService.findGameById(currentPlayGameId);
        decks = new ArrayList<>();
        List<Deck> deckList = game.getDecks();
        for (Deck deck : deckList) {
            List<Card> cardCopies = new ArrayList<>();
            PlayDeckPair playDeckPair = new PlayDeckPair();
            playDeckPair.setDeckId(deck.getId());
            Stack<Card> cards = new Stack<>();
            deck.getCards().forEach(card -> {
                for (int i = 0; i < card.getCopiesCount() - 1; i++) {
                    cardCopies.add(card);
                }
            });
            cards.addAll(deck.getCards());
            cards.addAll(cardCopies);
            deck.setCards(cards);
            playDeckPair.setDeck(cards);
            playDeckPair.setWaste(new Stack<>());
            decks.add(playDeckPair);
        }
        modelAndView.addObject(game);
        modelAndView.setViewName("play");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/play")
    private ModelAndView playGame(Authentication authentication, PlayDto playDto, HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = new ModelAndView();
        ObjectMapper objectMapper = new ObjectMapper();

        if (authentication == null) {
            modelAndView.setViewName("redirect:/auth");
            return modelAndView;
        }

        String param = playDto.getDeckToTakeCardFrom();
        if (param != null) {
            Long id = Long.valueOf(param);
            Card card = Objects.requireNonNull(getDeckById(id)).takeCard();
            String json = objectMapper.writeValueAsString(getDeckById(id));
            response.setContentType("application/json");
            response.getWriter().println(json);
            return null;
        }

        param = playDto.getDeckToShuffle();
        if (param != null) {
            Long id = Long.valueOf(param);
            shuffleDeck(id);
            String json = objectMapper.writeValueAsString(getDeckById(id));
            response.setContentType("application/json");
            response.getWriter().println(json);
            return null;
        }
        return modelAndView;
    }

    private PlayDeckPair getDeckById(Long deckId) {
        for (PlayDeckPair deck : decks) {
            if (deck.getDeckId().equals(deckId)) {
                return deck;
            }
        }
        return null;
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
