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

    private ArrayList<PlayDeckPair> decks;

    @Value("${custom.file.storage}")
    private String filePath;

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
//    @RequestMapping(value = "/creator", method = RequestMethod.POST)
    private ModelAndView editGame(EditGameDto editGameDto, Authentication authentication, HttpServletResponse response, MultipartFile playGroundFile) throws IOException, ServletException {
        ModelAndView modelAndView = new ModelAndView();
        ObjectMapper objectMapper = new ObjectMapper();
        if (authentication == null) {
            modelAndView.setViewName("redirect:/auth");
            return modelAndView;
        }

        User user = (User) authentication.getPrincipal();
        List<Game> games = gamesService.findGamesByUserId(user.getId());
        Game game;
        modelAndView.addObject(games);

        String editGameId = editGameDto.getCurrentEditGameId();
        Long currentEditGameId;
        if (editGameId != null && !editGameId.equals("false")) {
            currentEditGameId = Long.valueOf(editGameId);
            modelAndView.addObject("currentEditGameId", currentEditGameId);
        } else {
            return null;
        }
        String json = "";
        switch (editGameDto.getEditingAction()) {
            case "chooseGame" -> {
                game = gamesService.findGameById(currentEditGameId);
                json = objectMapper.writeValueAsString(game);
            }
            case "editGame" -> {
                String gameName = editGameDto.getGameName();
                String gameDescription = editGameDto.getGameDescription();
                gamesService.updateGameInfoById(currentEditGameId, gameName, gameDescription);
                game = gamesService.findGameById(currentEditGameId);
                json = objectMapper.writeValueAsString(game);
            }
            case "chooseCurrency" -> {
                String currentEditCurrencyId = editGameDto.getCurrentEditCurrencyId();
                if (currentEditCurrencyId != null) {
                    Currency currency = gamesService.findCurrencyById(Long.valueOf(currentEditCurrencyId));
                    json = objectMapper.writeValueAsString(currency);
                }
            }
            case "editCurrency" -> {
                String currentEditCurrencyId = editGameDto.getCurrentEditCurrencyId();
                String currencyName = editGameDto.getCurrencyName();
                String currencyDescription = editGameDto.getCurrencyDescription();
                if (currentEditCurrencyId != null && currencyName != null && currencyDescription != null) {
                    Currency currency = gamesService.updateCurrencyInfoById(Long.valueOf(currentEditCurrencyId), currencyName, currencyDescription);
                    json = objectMapper.writeValueAsString(currency);
                }
            }
            case "createCurrency" -> {
                String currencyName = editGameDto.getCurrencyName();
                String currencyDescription = editGameDto.getCurrencyDescription();
                if (currencyName != null && currencyDescription != null) {
                    CurrencyForm currencyForm = new CurrencyForm(currencyName, currencyDescription, currentEditGameId);
                    Currency currency = gamesService.addCurrency(currencyForm);
                    json = objectMapper.writeValueAsString(currency);
                }
            }
            case "chooseDeck" -> {
                String currentEditDeckId = editGameDto.getCurrentEditDeckId();
                if (currentEditDeckId != null && !currentEditDeckId.equals("create")) {
                    Deck deck = gamesService.findDeckById(Long.valueOf(currentEditDeckId));
                    json = objectMapper.writeValueAsString(deck);
                }
            }
            case "editDeck" -> {
                String currentEditDeckId = editGameDto.getCurrentEditDeckId();
                String deckName = editGameDto.getDeckName();
                String deckDescription = editGameDto.getDeckDescription();
                if (currentEditDeckId != null) {
                    Deck deck = gamesService.updateDeckInfoById(Long.valueOf(currentEditDeckId), deckName, deckDescription);
                    json = objectMapper.writeValueAsString(deck);
                }
            }
            case "createDeck" -> {
                String deckName = editGameDto.getDeckName();
                String deckDescription = editGameDto.getDeckDescription();
                DeckForm deckForm = new DeckForm(deckName, deckDescription);
                Deck deck = gamesService.addDeck(deckForm, currentEditGameId);
                json = objectMapper.writeValueAsString(deck);
            }
            case "chooseDeckOfCards" -> {
                String deckToShowCards = editGameDto.getDeckToShowCards();
                if (deckToShowCards != null) {
                    List<Card> cards = gamesService.findCardsByDeckId(Long.valueOf(deckToShowCards));
                    json = objectMapper.writeValueAsString(cards);
                }
            }
            case "chooseCard" -> {
                String deckToShowCards = editGameDto.getDeckToShowCards();
                String currentEditCardId = editGameDto.getCurrentEditCardId();
                if (deckToShowCards != null && currentEditCardId != null) {
                    Card card = gamesService.findCardById(Long.valueOf(currentEditCardId));
                    json = objectMapper.writeValueAsString(card);
                }
            }
            case "editCard" -> {
                String deckToShowCards = editGameDto.getDeckToShowCards();
                String currentEditCardId = editGameDto.getCurrentEditCardId();
                String cardName = editGameDto.getCardName();
                String cardDescription = editGameDto.getCardDescription();
                String cardCurrencyIdString = editGameDto.getCardCurrencyId();
                String cardValue = editGameDto.getCardValue();
                String copiesCount = editGameDto.getCardCopiesCount();
                Long cardCurrencyId = null;
                if (cardCurrencyIdString != null) {
                    cardCurrencyId = Long.valueOf(cardCurrencyIdString);
                }
                if (deckToShowCards != null && currentEditCardId != null) {
                    Card card = gamesService.updateCardInfoById(Long.valueOf(currentEditCardId), cardName, cardDescription, cardCurrencyId, Integer.valueOf(cardValue), Integer.valueOf(copiesCount));
                    json = objectMapper.writeValueAsString(card);
                }
            }
            case "createCard" -> {
                String deckToShowCards = editGameDto.getDeckToShowCards();
                String cardName = editGameDto.getCardName();
                String cardDescription = editGameDto.getCardDescription();
                String cardCurrencyId = editGameDto.getCardCurrencyId();
                String cardValue = editGameDto.getCardValue();
                String copiesCount = editGameDto.getCardCopiesCount();
                if (deckToShowCards != null) {
                    CardForm cardForm = CardForm.builder()
                            .name(cardName)
                            .description(cardDescription)
                            .value(Integer.valueOf(cardValue))
                            .deckId(Long.valueOf(deckToShowCards))
                            .copiesCount(Integer.valueOf(copiesCount))
                            .build();
                    if (cardCurrencyId != null) {
                        cardForm.setCurrencyId(Long.valueOf(cardCurrencyId));
                    }
                    Card card = gamesService.addCard(cardForm);
                    json = objectMapper.writeValueAsString(card);
                }
            }
            case "setPlayGround" -> {
                MultipartFile f = editGameDto.getPlayGroundFile();
                if (f != null) {
                    json = objectMapper.writeValueAsString(f);
                }
            }
        }
        System.out.println(json);
        response.setContentType("application/json");
        response.getWriter().println(json);
        return null;
    }

//    @PostMapping(value = "/files")
//    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {
////        logger.info("Загружаем файл");
//        String fileName = file.getOriginalFilename();
//        try {
//            file.transferTo(new File(filePath + fileName));
//        } catch (IOException e) {
////            logger.error("Произошла ошибка во время загрузки файла");
//            return ResponseEntity.internalServerError().build();
//        }
////        logger.info("Файл успешно загружен");
//        return ResponseEntity.ok("The file was successfully uploaded");
//    }

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
