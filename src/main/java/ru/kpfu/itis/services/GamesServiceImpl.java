package ru.kpfu.itis.services;

import ru.kpfu.itis.form.CardForm;
import ru.kpfu.itis.form.CurrencyForm;
import ru.kpfu.itis.form.DeckForm;
import ru.kpfu.itis.form.GameForm;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.model.Currency;
import ru.kpfu.itis.model.Deck;
import ru.kpfu.itis.model.Game;
import ru.kpfu.itis.repositories.CardsRepository;
import ru.kpfu.itis.repositories.CurrencyRepository;
import ru.kpfu.itis.repositories.DecksRepository;
import ru.kpfu.itis.repositories.GamesRepository;

import java.util.List;

public class GamesServiceImpl implements GamesService {

    private GamesRepository gamesRepository;
    private DecksRepository decksRepository;
    private CardsRepository cardsRepository;
    private CurrencyRepository currencyRepository;

    public GamesServiceImpl(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    public GamesServiceImpl(GamesRepository gamesRepository, DecksRepository decksRepository, CardsRepository cardsRepository, CurrencyRepository currencyRepository) {
        this.gamesRepository = gamesRepository;
        this.decksRepository = decksRepository;
        this.cardsRepository = cardsRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Game addGame(GameForm gameForm) {
        Game game = new Game();
        game.setName(gameForm.getName());
        game.setDescription(gameForm.getDescription());

        return gamesRepository.save(game);
    }

    @Override
    public Deck addDeck(DeckForm deckForm) {
        Deck deck = new Deck();
        deck.setName(deckForm.getName());
        deck.setDescription(deckForm.getDescription());

        return decksRepository.save(deck);
    }

    @Override
    public Card addCard(CardForm cardForm) {
        Card card = new Card();
        card.setName(cardForm.getName());
        card.setDescription(cardForm.getDescription());
        return cardsRepository.save(card);
    }

    @Override
    public Currency addCurrency(CurrencyForm currencyForm) {
        Currency game = new Currency();
        game.setName(currencyForm.getName());
        game.setDescription(currencyForm.getDescription());

        return currencyRepository.save(game);
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
    public List<Game> findAllGames() {
        return gamesRepository.findAll();
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
        return currencyRepository.findAll();
    }

    @Override
    public Game findGameById(Long gameId) {
        return null;
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
    public List<Card> findCardsByDeckId(Long gameId) {
        return null;
    }

    @Override
    public List<Game> findGamesByUserId(Long userId) {
        return gamesRepository.findGamesByUserId(userId);
    }
}
