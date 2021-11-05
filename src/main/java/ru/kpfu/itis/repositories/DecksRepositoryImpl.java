package ru.kpfu.itis.repositories;

import ru.kpfu.itis.model.Deck;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class DecksRepositoryImpl implements DecksRepository {

    private Connection connection;

    //language=sql
    private final String INSERT_DECK = "INSERT INTO deck (deck_name, deck_description) VALUES (?,?)";
    private final String LINK_DECK_TO_GAME = "INSERT INTO game_decks (game_id, deck_id) VALUES (?,?)";
    private final String FIND_DECKS_BY_GAME_ID = "SELECT * FROM deck WHERE game_id=?";
    private final String FIND_ALL = "SELECT * FROM deck;";

    public DecksRepositoryImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Deck> findAll() {
        return null;
    }

    @Override
    public Optional<Deck> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Deck save(Deck deck) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Deck> findDecksByGameId(Long gameId) {
        return null;
    }
}
