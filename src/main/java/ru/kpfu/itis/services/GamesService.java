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

    Deck addDeck(DeckForm deckForm, Long gameId);

    Card addCard(CardForm cardForm);

    Currency addCurrency(CurrencyForm currencyForm);

    void removeGame(Long gameId);

    void removeDeck(Long deckId);

    void removeCard(Long cardId);

    void removeCurrency(Long currencyId);

    void linkGameToUser(Long gameId, Long userId);

    void deleteDuplicateCards(Integer uniquenessToken, Integer count);

    List<Game> findAllGames();

    List<Deck> findAllDecks();

    List<Card> findAllCards();

    List<Currency> findAllCurrencies();

    Game findGameById(Long gameId);

    Deck findDeckById(Long deckId);

    Card findCardById(Long cardId);

    Currency findCurrencyById(Long currencyId);

    List<Deck> findDecksByGameId(Long gameId);

    List<Currency> findCurrenciesByGameId(Long gameId);

    List<Card> findCardsByDeckId(Long deckId);

    List<Game> findGamesByUserId(Long userId);

    Game updateGameInfoById(Long id, String name, String description);

    Currency updateCurrencyInfoById(Long id, String name, String description);

    Deck updateDeckInfoById(Long id, String name, String description);

    Card updateCardInfoById(Long id, String name, String description, Long currencyId, Integer value);
}
