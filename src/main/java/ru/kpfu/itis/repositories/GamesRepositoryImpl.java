package ru.kpfu.itis.repositories;

import ru.kpfu.itis.mapper.RowMapper;
import ru.kpfu.itis.model.Game;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GamesRepositoryImpl implements GamesRepository {

    private final Connection connection;

    //language=sql
    private final String INSERT_GAME = "INSERT INTO game(game_name, game_description) VALUES (?, ?)";
    private final String FIND_GAMES_BY_USER_ID = "SELECT g.id, g.game_name, g.game_description FROM game g INNER JOIN user_games u_g ON g.id = u_g.game_id INNER JOIN users ON u_g.user_id = users.id WHERE user_id=?;";
    private final String FIND_ALL = "SELECT * FROM game;";
    private final String LINK_GAME_TO_USER = "INSERT INTO user_games (user_id, game_id) VALUES (?,?)";


    public GamesRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Game> findAll() {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            resultSet = preparedStatement.executeQuery();
            List<Game> games = rowMapGames.rowMap(resultSet);
            return games;
        } catch (SQLException e) {

        }
        return null;
    }

    @Override
    public Optional<Game> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Game save(Game game) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_GAME, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, game.getName());
            preparedStatement.setString(2, game.getDescription());
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
    public List<Game> findGamesByUserId(Long userId) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_GAMES_BY_USER_ID);
            preparedStatement.setLong(1, userId);
            resultSet = preparedStatement.executeQuery();
            List<Game> games = rowMapGames.rowMap(resultSet);
            return games;
        } catch (SQLException e) {

        }
        return null;
    }

    @Override
    public void linkGameToUser(Long gameId, Long userId) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(LINK_GAME_TO_USER);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, gameId);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {

        }
    }


    private RowMapper<Game> rowMapper = ((resultSet) -> {
        if (resultSet.next()) {
            Game game = new Game();
            game.setId(resultSet.getLong("id"));
            game.setName(resultSet.getString("game_name"));
            game.setDescription(resultSet.getString("game_description"));
            return game;
        } else {
            return null;
        }
    });


    private RowMapper<List<Game>> rowMapGames = ((resultSet) -> {
        List<Game> products = new ArrayList<>();
        while (resultSet.next()) {
            Game game = new Game();
            game.setId(resultSet.getLong("id"));
            game.setName(resultSet.getString("game_name"));
            game.setDescription(resultSet.getString("game_description"));
            products.add(game);
        }
        return products;
    });

}
