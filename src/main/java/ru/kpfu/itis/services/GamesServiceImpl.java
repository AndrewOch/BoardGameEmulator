package ru.kpfu.itis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.models.entities.*;
import ru.kpfu.itis.models.form.CardForm;
import ru.kpfu.itis.models.form.CurrencyForm;
import ru.kpfu.itis.models.form.DeckForm;
import ru.kpfu.itis.models.form.GameForm;
import ru.kpfu.itis.repositories.*;
import ru.kpfu.itis.services.interfaces.GamesService;

import java.util.ArrayList;
import java.util.Iterator;
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
    @Autowired
    private UserGamesRepository userGamesRepository;
    @Autowired
    private GameDecksRepository gameDecksRepository;

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
        GameDecks gameDecks = GameDecks.builder()
                .gameId(gameId)
                .deckId(deck.getId())
                .build();
        gameDecksRepository.save(gameDecks);
        return deck;
    }

    @Override
    public Card addCard(CardForm cardForm) {
        Card card = new Card();
        card.setName(cardForm.getName());
        card.setDescription(cardForm.getDescription());
        card.setValue(cardForm.getValue());
        card.setCurrencyId(cardForm.getCurrencyId());
        card.setDeck(Deck.builder().id(cardForm.getDeckId()).build());
        return cardsRepository.save(card);
    }

    @Override
    public Currency addCurrency(CurrencyForm currencyForm) {
        Currency currency = new Currency();
        currency.setName(currencyForm.getName());
        currency.setDescription(currencyForm.getDescription());
        currency.setGame(Game.builder().id(currencyForm.getGameId()).build());
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
        UserGames userGames = UserGames.builder()
                .userId(userId)
                .gameId(gameId)
                .build();
        userGamesRepository.save(userGames);
    }

    @Override
    public void deleteDuplicateCards(Integer uniquenessToken, Integer count) {
        //TODO
        //cardsRepository.deleteDuplicates(uniquenessToken, count);
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
        game.setCurrencies(currencyRepository.findAllByGameId(gameId));

        ArrayList<Deck> decks = (ArrayList<Deck>) decksRepository.findDecksByGameId(gameId);

        for (Deck deck : decks) {
            deck.setCards(cardsRepository.findAllByDeckId(deck.getId()));
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
        deck.setCards(findCardsByDeckId(deckId));
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
        return currencyRepository.findAllByGameId(gameId);
    }

    @Override
    public List<Card> findCardsByDeckId(Long deckId) {
        return cardsRepository.findAllByDeckId(deckId);
    }

    @Override
    public List<Game> findGamesByUserId(Long userId) {
        List<Game> games = new ArrayList<>();
        List<Object[]> results = gamesRepository.findAllByUserId(userId);
        for(Object[] entry: results){
            Game game = new Game();
            game.setId((Long) entry[0]);
            game.setName(String.valueOf(entry[1]));
            game.setDescription(String.valueOf(entry[2]));
            game.setDecks(decksRepository.findDecksByGameId(game.getId()));
            game.setCurrencies(currencyRepository.findAllByGameId(game.getId()));
            games.add(game);
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
