package ru.kpfu.itis.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.models.dtos.EditGameDto;
import ru.kpfu.itis.models.entities.Card;
import ru.kpfu.itis.models.entities.Currency;
import ru.kpfu.itis.models.entities.Deck;
import ru.kpfu.itis.models.entities.Game;
import ru.kpfu.itis.models.forms.CardForm;
import ru.kpfu.itis.models.forms.CurrencyForm;
import ru.kpfu.itis.models.forms.DeckForm;
import ru.kpfu.itis.services.interfaces.GamesService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class EditGameService {

    private final Logger logger = LoggerFactory.getLogger(EditGameService.class);

    @Value("${custom.absolute.file.storage}")
    private String absoluteFilePath;

    @Value("${custom.file.storage}")
    private String filePath;

    @Autowired
    @Qualifier(value = "gamesService")
    private GamesService gamesService;

    public String editGame(EditGameDto editGameDto) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        Game game;
        switch (editGameDto.getEditingAction()) {
            case "chooseGame" -> {
                game = gamesService.findGameById(Long.valueOf(editGameDto.getCurrentEditGameId()));
                json = objectMapper.writeValueAsString(game);
            }
            case "editGame" -> {
                String gameName = editGameDto.getGameName();
                String gameDescription = editGameDto.getGameDescription();
                gamesService.updateGameInfoById(Long.valueOf(editGameDto.getCurrentEditGameId()), gameName, gameDescription);
                game = gamesService.findGameById(Long.valueOf(editGameDto.getCurrentEditGameId()));
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
                    CurrencyForm currencyForm = new CurrencyForm(currencyName, currencyDescription, Long.valueOf(editGameDto.getCurrentEditGameId()));
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
                Deck deck = gamesService.addDeck(deckForm, Long.valueOf(editGameDto.getCurrentEditGameId()));
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
                MultipartFile file = editGameDto.getPlayGroundFile();
                logger.info("Загружаем файл");
                String fileName = file.getOriginalFilename();
                try {
                    file.transferTo(new File(absoluteFilePath + fileName));
                    gamesService.setGamePlayGround(Long.valueOf(editGameDto.getCurrentEditGameId()), fileName);
                } catch (IOException e) {
                    logger.error("Произошла ошибка во время загрузки файла");
                    return "Произошла ошибка во время загрузки файла";
                }
                logger.info("Файл успешно загружен");
                json = objectMapper.writeValueAsString(filePath + fileName);
            }
        }
        return json;
    }
}
