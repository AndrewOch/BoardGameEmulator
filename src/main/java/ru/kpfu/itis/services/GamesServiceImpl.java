package ru.kpfu.itis.services;

import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.models.entities.*;
import ru.kpfu.itis.models.forms.CardForm;
import ru.kpfu.itis.models.forms.CurrencyForm;
import ru.kpfu.itis.models.forms.DeckForm;
import ru.kpfu.itis.models.forms.GameForm;
import ru.kpfu.itis.repositories.*;
import ru.kpfu.itis.services.interfaces.GamesService;

import java.sql.Timestamp;
import java.time.Instant;
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
    @Autowired
    private UserGamesRepository userGamesRepository;
    @Autowired
    private GameDecksRepository gameDecksRepository;

    @Override
    public Game addGame(GameForm gameForm) {
        Game game = new Game();
        game.setName(gameForm.getName());
        game.setDescription(gameForm.getDescription());
        game.setCreatedAt(Timestamp.from(Instant.now()));
        game = gamesRepository.save(game);
        linkGameToUser(game.getId(), gameForm.getUser().getId());
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
        Card card = Card.builder()
                .name(cardForm.getName())
                .description(cardForm.getDescription())
                .value(cardForm.getValue())
                .currencyId(cardForm.getCurrencyId())
                .deck(Deck.builder().id(cardForm.getDeckId()).build())
                .copiesCount(cardForm.getCopiesCount())
                .build();
        return cardsRepository.save(card);
    }

    @Override
    public Currency addCurrency(CurrencyForm currencyForm) {
        Currency currency = new Currency();
        currency.setName(currencyForm.getName());
        currency.setDescription(currencyForm.getDescription());
        currency.setGame(Game.builder().id(currencyForm.getGameId()).build());
        currency.setCreatedAt(Timestamp.from(Instant.now()));
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
        List<Game> games = gamesRepository.findAll();

        for (Game game : games) {
            List<Deck> decks = new ArrayList<>();
            for (Object[] array : decksRepository.findDecksByGameId(game.getId())) {
                Deck deck = new Deck();
                deck.setId((Long) array[0]);
                deck.setName((String) array[1]);
                deck.setDescription((String) array[2]);
                decks.add(deck);
            }
            game.setDecks(decks);
            game.setCurrencies(currencyRepository.findAllByGameId(game.getId()));
        }
        return games;
    }

    @Override
    public List<Deck> findAllDecks() {
        return decksRepository.findAll();
    }

    @Override
    public List<Card> findAllCards() {
        return cardsRepository.findAll();
    }

    @Override
    public List<Currency> findAllCurrencies() {
        return currencyRepository.findAll();
    }

    @Override
    public Game findGameById(Long gameId) {
        Optional<Game> optionalGame = gamesRepository.findById(gameId);
        if (optionalGame.isEmpty()) {
            return null;
        }

        Game game = optionalGame.get();

        List<Currency> currencies = currencyRepository.findAllByGameId(gameId);
        currencies.forEach(currency -> currency.setGame(null));
        game.setCurrencies(currencies);

        ArrayList<Deck> decks = new ArrayList<>();
        try {
            for (Object[] array : decksRepository.findDecksByGameId(gameId)) {
                Deck deck = new Deck();
                deck.setId((Long) array[0]);
                deck.setName((String) array[1]);
                deck.setDescription((String) array[2]);
                decks.add(deck);
            }
        } catch (LazyInitializationException e) {
            System.out.println(e);
        }
        for (Deck deck : decks) {
            deck.setCards(findCardsByDeckId(deck.getId()));
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
        Card card = optional.get();
        card.setDeck(null);
        return card;
    }

    @Override
    public Currency findCurrencyById(Long currencyId) {
        Optional<Currency> optional = currencyRepository.findById(currencyId);
        if (optional.isEmpty()) {
            return null;
        }
        Currency currency = optional.get();
        currency.setGame(null);
        return currency;
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
        List<Card> cards = cardsRepository.findAllByDeckId(deckId);
        cards.forEach(card -> card.setDeck(null));
        return cards;
    }

    @Override
    public List<Game> findGamesByUserId(Long userId) {
        List<Game> games = new ArrayList<>();
        List<Object[]> results = gamesRepository.findAllByUserId(userId);
        for (Object[] entry : results) {
            Game game = new Game();
            game.setId((Long) entry[0]);
            game.setName(String.valueOf(entry[1]));
            game.setDescription(String.valueOf(entry[2]));

            List<Deck> decks = new ArrayList<>();
            for (Object[] array : decksRepository.findDecksByGameId(game.getId())) {
                Deck deck = new Deck();
                deck.setId((Long) array[0]);
                deck.setName((String) array[1]);
                deck.setDescription((String) array[2]);
                decks.add(deck);
            }

            game.setDecks(decks);
            game.setCurrencies(currencyRepository.findAllByGameId(game.getId()));
            games.add(game);
        }
        return games;
    }

    @Override
    public void updateGameInfoById(Long id, String name, String description) {
        gamesRepository.updateGameInfoById(id, name, description);
    }

    @Override
    public Currency updateCurrencyInfoById(Long id, String name, String description) {
        currencyRepository.updateCurrencyInfoById(id, name, description);
        Currency currency = currencyRepository.findById(id).get();
        currency.setGame(null);
        return currency;
    }

    @Override
    public Deck updateDeckInfoById(Long id, String name, String description) {
        decksRepository.updateDeckInfoById(id, name, description);
        return findDeckById(id);
    }

    @Override
    public Card updateCardInfoById(Long id, String name, String description, Long currencyId, Integer value, Integer copiesCount) {
        cardsRepository.updateCardInfoById(id, name, description, currencyId, value, copiesCount);
        return findCardById(id);
    }

}
