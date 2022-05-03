package ru.kpfu.itis.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.kpfu.itis.models.dtos.*;
import ru.kpfu.itis.models.entities.*;
import ru.kpfu.itis.models.forms.CardForm;
import ru.kpfu.itis.models.forms.CurrencyForm;
import ru.kpfu.itis.models.forms.DeckForm;
import ru.kpfu.itis.services.interfaces.GamesService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/creator")
public class GamesEditorController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Logger logger = LoggerFactory.getLogger(GamesEditorController.class);

    @Value("${custom.absolute.file.storage}")
    private String absoluteFilePath;

    @Value("${custom.file.storage}")
    private String filePath;

    @Autowired
    @Qualifier(value = "gamesService")
    private GamesService gamesService;

    @GetMapping
    public ModelAndView getEditGamePage(Authentication authentication, Long currentEditGameId) {
        if (authentication == null) return new ModelAndView("redirect:/auth");
        ModelAndView modelAndView = new ModelAndView();

        User user = (User) authentication.getPrincipal();
        if (currentEditGameId != null) {
            modelAndView.addObject("currentEditGameId", currentEditGameId);
            Game game = gamesService.findGameById(currentEditGameId);
            modelAndView.addObject("game", game);
        }
        List<Game> games = gamesService.findGamesByUserId(user.getId());
        modelAndView.addObject("games", games);
        modelAndView.setViewName("creator");
        return modelAndView;
    }

    @PostMapping
    public void chooseGame(Long currentEditGameId, HttpServletResponse response) throws IOException {
        Game game = gamesService.findGameById(currentEditGameId);
        String json = objectMapper.writeValueAsString(game);
        response.setContentType("application/json");
        response.getWriter().println(json);
    }

    @PutMapping("/edit_game")
    public void editGame(EditGameDto editGameDto, HttpServletResponse response) throws IOException {
        String gameName = editGameDto.getGameName();
        String gameDescription = editGameDto.getGameDescription();
        if (gameName != null && gameDescription != null) {
            gamesService.updateGameInfoById(editGameDto.getCurrentEditGameId(), gameName, gameDescription);
            Game game = gamesService.findGameById(editGameDto.getCurrentEditGameId());
            String json = objectMapper.writeValueAsString(game);
            response.setContentType("application/json");
            response.getWriter().println(json);
        }
    }

    @PostMapping("/choose_currency")
    public void chooseCurrency(Long currentEditCurrencyId, HttpServletResponse response) throws IOException {
        String json = "";
        if (currentEditCurrencyId != null) {
            Currency currency = gamesService.findCurrencyById(currentEditCurrencyId);
            json = objectMapper.writeValueAsString(currency);
        }
        response.setContentType("application/json");
        response.getWriter().println(json);
    }

    @PutMapping("/edit_currency")
    public void editCurrency(CurrencyDto currencyDto, HttpServletResponse response) throws IOException {
        Long currentEditCurrencyId = currencyDto.getCurrentEditCurrencyId();
        String currencyName = currencyDto.getCurrencyName();
        String currencyDescription = currencyDto.getCurrencyDescription();
        if (currentEditCurrencyId != null && currencyName != null && currencyDescription != null) {
            Currency currency = gamesService.updateCurrencyInfoById(currentEditCurrencyId, currencyName, currencyDescription);
            String json = objectMapper.writeValueAsString(currency);
            response.setContentType("application/json");
            response.getWriter().println(json);
        }
    }

    @PostMapping("/create_currency")
    public void createCurrency(CurrencyDto currencyDto, HttpServletResponse response) throws IOException {
        String currencyName = currencyDto.getCurrencyName();
        String currencyDescription = currencyDto.getCurrencyDescription();
        if (currencyName != null && currencyDescription != null) {
            CurrencyForm currencyForm = new CurrencyForm(currencyName, currencyDescription, currencyDto.getCurrentEditGameId());
            Currency currency = gamesService.addCurrency(currencyForm);
            String json = objectMapper.writeValueAsString(currency);
            response.setContentType("application/json");
            response.getWriter().println(json);
        }
    }

    @PostMapping("/choose_deck")
    public void chooseDeck(Long currentEditDeckId, HttpServletResponse response) throws IOException {
        String json = "";
        if (currentEditDeckId != null) {
            Deck deck = gamesService.findDeckById(currentEditDeckId);
            json = objectMapper.writeValueAsString(deck);
        }
        response.setContentType("application/json");
        response.getWriter().println(json);
    }

    @PutMapping("/edit_deck")
    public void editDeck(DeckDto deckDto, HttpServletResponse response) throws IOException {
        Long currentEditDeckId = deckDto.getCurrentEditDeckId();
        String deckName = deckDto.getDeckName();
        String deckDescription = deckDto.getDeckDescription();
        if (currentEditDeckId != null) {
            Deck deck = gamesService.updateDeckInfoById(currentEditDeckId, deckName, deckDescription);
            String json = objectMapper.writeValueAsString(deck);
            response.setContentType("application/json");
            response.getWriter().println(json);
        }
    }

    @PostMapping("/create_deck")
    public void createDeck(DeckDto deckDto, HttpServletResponse response) throws IOException {
        String deckName = deckDto.getDeckName();
        String deckDescription = deckDto.getDeckDescription();
        DeckForm deckForm = new DeckForm(deckName, deckDescription);
        Deck deck = gamesService.addDeck(deckForm, deckDto.getCurrentEditGameId());
        String json = objectMapper.writeValueAsString(deck);
        response.setContentType("application/json");
        response.getWriter().println(json);
    }

    @PostMapping("/choose_deck_of_cards")
    public void chooseDeckOfCards(Long deckToShowCards, HttpServletResponse response) throws IOException {
        if (deckToShowCards != null) {
            List<Card> cards = gamesService.findCardsByDeckId(deckToShowCards);
            String json = objectMapper.writeValueAsString(cards);
            response.setContentType("application/json");
            response.getWriter().println(json);
        }
    }

    @PostMapping("/choose_card")
    public void chooseCard(CardDto cardDto, HttpServletResponse response) throws IOException {
        Long deckToShowCards = cardDto.getDeckToShowCards();
        Long currentEditCardId = cardDto.getCurrentEditCardId();
        if (deckToShowCards != null && currentEditCardId != null) {
            Card card = gamesService.findCardById(currentEditCardId);
            String json = objectMapper.writeValueAsString(card);
            response.setContentType("application/json");
            response.getWriter().println(json);
        }
    }

    @PutMapping("/edit_card")
    public void editCard(CardDto cardDto, HttpServletResponse response) throws IOException {
        Long deckToShowCards = cardDto.getDeckToShowCards();
        Long currentEditCardId = cardDto.getCurrentEditCardId();
        String cardName = cardDto.getCardName();
        String cardDescription = cardDto.getCardDescription();
        Long cardCurrencyId = cardDto.getCardCurrencyId();
        Integer cardValue = cardDto.getCardValue();
        Integer copiesCount = cardDto.getCardCopiesCount();

        if (deckToShowCards != null && currentEditCardId != null) {
            Card card = gamesService.updateCardInfoById(currentEditCardId, cardName, cardDescription, cardCurrencyId, cardValue, copiesCount);
            String json = objectMapper.writeValueAsString(card);
            response.setContentType("application/json");
            response.getWriter().println(json);
        }
    }

    @PostMapping("/create_card")
    public void createCard(CardDto cardDto, HttpServletResponse response) throws IOException {
        Long deckToShowCards = cardDto.getDeckToShowCards();
        String cardName = cardDto.getCardName();
        String cardDescription = cardDto.getCardDescription();
        Long cardCurrencyId = cardDto.getCardCurrencyId();
        Integer cardValue = cardDto.getCardValue();
        Integer copiesCount = cardDto.getCardCopiesCount();
        if (deckToShowCards != null) {
            CardForm cardForm = CardForm.builder()
                    .name(cardName)
                    .description(cardDescription)
                    .value(cardValue)
                    .deckId(deckToShowCards)
                    .copiesCount(copiesCount)
                    .build();
            if (cardCurrencyId != null) {
                cardForm.setCurrencyId(cardCurrencyId);
            }
            Card card = gamesService.addCard(cardForm);
            String json = objectMapper.writeValueAsString(card);
            response.setContentType("application/json");
            response.getWriter().println(json);
        }
    }

    @PostMapping("/set_playground")
    public void setPlayground(PlaygroundDto playgroundDto, HttpServletResponse response) throws IOException {
        MultipartFile file = playgroundDto.getPlayGroundFile();
        logger.info("Загружаем файл");
        String fileName = file.getOriginalFilename();
        try {
            file.transferTo(new File(absoluteFilePath + fileName));
            gamesService.setGamePlayGround(playgroundDto.getCurrentEditGameId(), fileName);
        } catch (IOException e) {
            logger.error("Произошла ошибка во время загрузки файла");
        }
        logger.info("Файл успешно загружен");
        String json = objectMapper.writeValueAsString(filePath + fileName);
        response.setContentType("application/json");
        response.getWriter().println(json);
    }
}
