package ru.kpfu.itis.services;

import ru.kpfu.itis.form.CardForm;
import ru.kpfu.itis.form.CurrencyForm;
import ru.kpfu.itis.form.DeckForm;
import ru.kpfu.itis.form.GameForm;
import ru.kpfu.itis.model.Card;
import ru.kpfu.itis.model.Currency;
import ru.kpfu.itis.model.Deck;
import ru.kpfu.itis.model.Game;

import java.util.List;

public interface GamesService {

    Game addGame(GameForm gameForm);

    Deck addDeck(DeckForm deckForm);

    Card addCard(CardForm cardForm);

    Currency addCurrency(CurrencyForm currencyForm);

    void removeGame(Long gameId);

    void removeDeck(Long deckId);

    void removeCard(Long cardId);

    void removeCurrency(Long currencyId);

    void linkGameToUser(Long gameId, Long userId);

    List<Game> findAllGames();

    List<Deck> findAllDecks();

    List<Card> findAllCards();

    List<Currency> findAllCurrencies();

    Game findGameById(Long gameId);

    List<Deck> findDecksByGameId(Long gameId);

    List<Currency> findCurrenciesByGameId(Long gameId);

    List<Card> findCardsByDeckId(Long gameId);

    List<Game> findGamesByUserId(Long userId);
}
