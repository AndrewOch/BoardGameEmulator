package ru.kpfu.itis.repositories;

import ru.kpfu.itis.mapper.RowMapper;
import ru.kpfu.itis.model.Deck;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DecksRepositoryImpl implements DecksRepository {

    private Connection connection;

    //language=sql
    private final String INSERT_DECK = "INSERT INTO deck (deck_name, deck_description) VALUES (?,?)";
    private final String LINK_DECK_TO_GAME = "INSERT INTO game_decks (game_id, deck_id) VALUES (?,?)";
    private final String FIND_DECKS_BY_GAME_ID = "SELECT deck.id, deck.deck_name, deck.deck_description FROM deck inner join game_decks gd on deck.id = gd.deck_id WHERE game_id=?";
    private final String FIND_ALL = "SELECT * FROM deck;";
    private final String FIND_BY_ID = "SELECT * FROM deck WHERE id=?";
    private final String UPDATE_DECK_INFO_BY_ID = "update deck set deck_name = ?, deck_description = ? where id = ?;";


    public DecksRepositoryImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Deck> findAll() {
        return null;
    }

    @Override
    public Optional<Deck> findById(Long id) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            return Optional.ofNullable(rowMapper.rowMap(resultSet));
        } catch (SQLException e) {
        }
        return Optional.empty();
    }

    @Override
    public Deck save(Deck deck) {

        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DECK, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, deck.getName());
            preparedStatement.setString(2, deck.getDescription());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            return rowMapper.rowMap(resultSet);

        } catch (SQLException e) {

        }
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Deck> findDecksByGameId(Long gameId) {

        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_DECKS_BY_GAME_ID);
            preparedStatement.setLong(1, gameId);
            resultSet = preparedStatement.executeQuery();
            return rowMapGames.rowMap(resultSet);
        } catch (SQLException e) {
        }
        return new ArrayList<>();
    }

    @Override
    public Deck updateDeckInfoById(Long id, String name, String description) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_DECK_INFO_BY_ID, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            return rowMapper.rowMap(resultSet);

        } catch (SQLException e) {

        }
        return null;
    }

    @Override
    public void linkDeckToGame(Long deckId, Long gameId) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(LINK_DECK_TO_GAME);
            preparedStatement.setLong(1, gameId);
            preparedStatement.setLong(2, deckId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    private RowMapper<Deck> rowMapper = ((resultSet) -> {
        if (resultSet.next()) {
            Deck deck = new Deck();
            deck.setId(resultSet.getLong("id"));
            deck.setName(resultSet.getString("deck_name"));
            deck.setDescription(resultSet.getString("deck_description"));
            return deck;
        } else {
            return null;
        }
    });

    private RowMapper<List<Deck>> rowMapGames = ((resultSet) -> {
        List<Deck> decks = new ArrayList<>();
        while (resultSet.next()) {
            Deck deck = new Deck();
            deck.setId(resultSet.getLong("id"));
            deck.setName(resultSet.getString("deck_name"));
            deck.setDescription(resultSet.getString("deck_description"));
            decks.add(deck);
        }
        return decks;
    });
}
