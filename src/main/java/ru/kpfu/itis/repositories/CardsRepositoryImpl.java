package ru.kpfu.itis.repositories;

import ru.kpfu.itis.model.Card;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class CardsRepositoryImpl implements CardsRepository {

    private Connection connection;

    //language=sql
    private final String INSERT_CARD = "INSERT INTO card (title, description, currency_id, value) VALUES (?,?,?,?)";
    private final String FIND_CARDS_BY_DECK_ID = "SELECT * FROM card WHERE deck_id=?";
    private final String FIND_ALL = "SELECT * FROM card;";

    public CardsRepositoryImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Card> findCardsByDeckId(Long deckId) {
        return null;
    }

    @Override
    public List<Card> findAll() {
        return null;
    }

    @Override
    public Optional<Card> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Card save(Card card) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
