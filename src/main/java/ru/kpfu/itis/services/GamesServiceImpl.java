package ru.kpfu.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.models.form.CardForm;
import ru.kpfu.itis.models.form.CurrencyForm;
import ru.kpfu.itis.models.form.DeckForm;
import ru.kpfu.itis.models.form.GameForm;
import ru.kpfu.itis.models.entities.Card;
import ru.kpfu.itis.models.entities.Currency;
import ru.kpfu.itis.models.entities.Deck;
import ru.kpfu.itis.models.entities.Game;
import ru.kpfu.itis.repositories.interfaces.CardsRepository;
import ru.kpfu.itis.repositories.interfaces.CurrencyRepository;
import ru.kpfu.itis.repositories.interfaces.DecksRepository;
import ru.kpfu.itis.repositories.interfaces.GamesRepository;
import ru.kpfu.itis.services.interfaces.GamesService;
import ru.kpfu.itis.services.interfaces.UsersService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("gamesService")
public class GamesServiceImpl implements GamesService {

    @Autowired
    private GamesRepository gamesRepository;
    @Autowired
    private DecksRepository decksRepository;
    @Autowired
    private CardsRepository cardsRepository;
    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    public Game addGame(GameForm gameForm) {
        Game game = new Game();
        game.setName(gameForm.getName());
        game.setDescription(gameForm.getDescription());

        game = gamesRepository.save(game);
        return game;
    }

    @Override
    public Deck addDeck(DeckForm deckForm, Long gameId) {
        Deck deck = new Deck();
        deck.setName(deckForm.getName());
        deck.setDescription(deckForm.getDescription());
        deck = decksRepository.save(deck);
        decksRepository.linkDeckToGame(deck.getId(), gameId);
        return deck;
    }

    @Override
    public Card addCard(CardForm cardForm) {
        Card card = new Card();
        card.setName(cardForm.getName());
        card.setDescription(cardForm.getDescription());
        card.setValue(cardForm.getValue());
        card.setCurrencyId(cardForm.getCurrencyId());
        card.setDeckId(cardForm.getDeckId());
        return cardsRepository.save(card);
    }

    @Override
    public Currency addCurrency(CurrencyForm currencyForm) {
        Currency currency = new Currency();
        currency.setName(currencyForm.getName());
        currency.setDescription(currencyForm.getDescription());
        currency.setGameId(currencyForm.getGameId());
        return currencyRepository.save(currency);
    }

    @Override
    public void removeGame(Long gameId) {

    }

    @Override
    public void removeDeck(Long deckId) {

    }

    @Override
    public void removeCard(Long cardId) {

    }

    @Override
    public void removeCurrency(Long currencyId) {

    }

    @Override
    public void linkGameToUser(Long gameId, Long userId) {
        gamesRepository.linkGameToUser(gameId, userId);
    }

    @Override
    public void deleteDuplicateCards(Integer uniquenessToken, Integer count) {
        cardsRepository.deleteDuplicates(uniquenessToken, count);
    }

    @Override
    public List<Game> findAllGames() {
        return (List<Game>) gamesRepository.findAll();
    }

    @Override
    public List<Deck> findAllDecks() {
        return null;
    }

    @Override
    public List<Card> findAllCards() {
        return null;
    }

    @Override
    public List<Currency> findAllCurrencies() {
        return (List<Currency>) currencyRepository.findAll();
    }

    @Override
    public Game findGameById(Long gameId) {
        Optional<Game> optionalGame = gamesRepository.findById(gameId);
        if (optionalGame.isEmpty()) {
            return null;
        }

        Game game = optionalGame.get();
        game.setCurrencies((ArrayList<Currency>) currencyRepository.findCurrenciesByGameId(gameId));

        ArrayList<Deck> decks = (ArrayList<Deck>) decksRepository.findDecksByGameId(gameId);

        for (Deck deck : decks) {
            deck.setCards((ArrayList<Card>) cardsRepository.findCardsByDeckId(deck.getId()));
        }

        game.setDecks(decks);

        return game;
    }

    @Override
    public Deck findDeckById(Long deckId) {
        Optional<Deck> optionalGame = decksRepository.findById(deckId);
        if (optionalGame.isEmpty()) {
            return null;
        }
        Deck deck = optionalGame.get();
        deck.setCards((ArrayList<Card>) findCardsByDeckId(deckId));
        return deck;
    }

    @Override
    public Card findCardById(Long cardId) {
        Optional<Card> optional = cardsRepository.findById(cardId);
        if (optional.isEmpty()) {
            return null;
        }
        return optional.get();
    }

    @Override
    public Currency findCurrencyById(Long currencyId) {
        Optional<Currency> optional = currencyRepository.findById(currencyId);
        if (optional.isEmpty()) {
            return null;
        }
        return optional.get();
    }

    @Override
    public List<Deck> findDecksByGameId(Long gameId) {
        return null;
    }

    @Override
    public List<Currency> findCurrenciesByGameId(Long gameId) {
        return currencyRepository.findCurrenciesByGameId(gameId);
    }

    @Override
    public List<Card> findCardsByDeckId(Long deckId) {
        return cardsRepository.findCardsByDeckId(deckId);
    }

    @Override
    public List<Game> findGamesByUserId(Long userId) {
        List<Game> games = (List<Game>) gamesRepository.findGamesByUserId(userId);
        for (Game game : games) {
            game.setDecks((ArrayList<Deck>) decksRepository.findDecksByGameId(game.getId()));
            game.setCurrencies((ArrayList<Currency>) currencyRepository.findCurrenciesByGameId(game.getId()));
        }
        return games;
    }

    @Override
    public Game updateGameInfoById(Long id, String name, String description) {
        return gamesRepository.updateGameInfoById(id, name, description).get();
    }

    @Override
    public Currency updateCurrencyInfoById(Long id, String name, String description) {
        return currencyRepository.updateCurrencyInfoById(id, name, description).get();
    }

    @Override
    public Deck updateDeckInfoById(Long id, String name, String description) {
        return decksRepository.updateDeckInfoById(id, name, description).get();
    }

    @Override
    public Card updateCardInfoById(Long id, String name, String description, Long currencyId, Integer value) {
        return cardsRepository.updateCardInfoById(id, name, description, currencyId, value).get();
    }

}
