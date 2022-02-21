package ru.kpfu.itis.repositories;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.kpfu.itis.model.Deck;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DecksRepositoryImpl implements DecksRepository {

    private JdbcTemplate jdbcTemplate;

    private RowMapper<Deck> rowMapper = ((resultSet, rowNum) -> {
        return Deck.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("deck_name"))
                .description(resultSet.getString("deck_description"))
                .build();
    });

    private RowMapper<List<Deck>> listRowMapper = ((resultSet, rowNum) -> {
        List<Deck> decks = new ArrayList<>();
        while (resultSet.next()) {
            decks.add(Deck.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("deck_name"))
                    .description(resultSet.getString("deck_description"))
                    .build());
        }
        return decks;
    });

    //language=sql
    private final String SQL_INSERT_DECK = "INSERT INTO deck (deck_name, deck_description) VALUES (?,?)";
    private final String SQL_LINK_DECK_TO_GAME = "INSERT INTO game_decks (game_id, deck_id) VALUES (?,?)";
    private final String SQL_FIND_DECKS_BY_GAME_ID = "SELECT deck.id, deck.deck_name, deck.deck_description FROM deck inner join game_decks gd on deck.id = gd.deck_id WHERE game_id=?";
    private final String SQL_FIND_ALL = "SELECT * FROM deck;";
    private final String SQL_FIND_BY_ID = "SELECT * FROM deck WHERE id=?";
    private final String SQL_UPDATE_DECK_INFO_BY_ID = "update deck set deck_name = ?, deck_description = ? where id = ?;";


    public DecksRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <S extends Deck> S save(S entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_DECK, new String[]{"id"});

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());

            return statement;
        }, keyHolder);
        entity.setId(keyHolder.getKey().longValue());
        return entity;
    }

    @Override
    public <S extends Deck> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Deck> findById(Long aLong) {
        Deck deck;
        try {
            deck = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, rowMapper, aLong);
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
        return Optional.of(deck);
    }

    @Override
    public List<Deck> findDecksByGameId(Long gameId) {

        List<Deck> decks;
        try {
            decks = jdbcTemplate.queryForObject(SQL_FIND_DECKS_BY_GAME_ID, listRowMapper);
        } catch (DataAccessException ex) {
            return new ArrayList<>();
        }
        return decks;
    }


    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Deck> findAll() {
        Iterable<Deck> decks;
        try {
            decks = jdbcTemplate.queryForObject(SQL_FIND_ALL, listRowMapper);
        } catch (DataAccessException ex) {
            return new ArrayList<>();
        }
        return decks;
    }

    @Override
    public Iterable<Deck> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Deck entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Deck> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Optional<Deck> updateDeckInfoById(Long id, String name, String description) {
        Deck deck = null;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_DECK_INFO_BY_ID, new String[]{"id"});

            statement.setString(1, name);
            statement.setString(2, description);
            statement.setLong(3, id);
            return statement;
        }, keyHolder);
        deck.setId(keyHolder.getKey().longValue());
        return Optional.of(deck);
    }

    @Override
    public void linkDeckToGame(Long deckId, Long gameId) {
        //TODO Уточнить метод
//        ResultSet resultSet = null;
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(SQL_LINK_DECK_TO_GAME);
//            preparedStatement.setLong(1, gameId);
//            preparedStatement.setLong(2, deckId);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//        }
    }

}
