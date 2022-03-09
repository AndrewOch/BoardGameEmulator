package ru.kpfu.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.models.entities.Game;
import ru.kpfu.itis.repositories.interfaces.GamesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("gamesRepositoryJdbcTemplateImpl")
public class GamesRepositoryImpl implements GamesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Game> rowMapper = ((resultSet, rowNum) -> {
        return Game.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("game_name"))
                .description(resultSet.getString("game_description"))
                .build();
    });

    //language=sql
    private final String SQL_INSERT_GAME = "INSERT INTO game(game_name, game_description) VALUES (?, ?)";
    private final String SQL_FIND_GAMES_BY_USER_ID = "SELECT g.id, g.game_name, g.game_description FROM game g INNER JOIN user_games u_g ON g.id = u_g.game_id INNER JOIN users ON u_g.user_id = users.id WHERE user_id=?;";
    private final String SQL_FIND_ALL = "SELECT * FROM game;";
    private final String SQL_LINK_GAME_TO_USER = "INSERT INTO user_games (game_id, user_id) VALUES (?,?)";
    private final String SQL_FIND_GAME_BY_ID = "SELECT * FROM game WHERE id=?;";
    private final String SQL_UPDATE_GAME_INFO_BY_ID = "update game set game_name = ?, game_description = ? where id = ?;";

    @Override
    public <S extends Game> S save(S entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_GAME, new String[]{"id"});

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());

            return statement;
        }, keyHolder);
        entity.setId(keyHolder.getKey().longValue());
        return entity;
    }

    @Override
    public <S extends Game> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Game> findById(Long aLong) {
        Game game;
        try {
            game = jdbcTemplate.queryForObject(SQL_FIND_GAME_BY_ID, rowMapper, aLong);
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
        return Optional.ofNullable(game);
    }


    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Game> findAll() {
        Iterable<Game> games;
        try {
            games = jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
        } catch (DataAccessException ex) {
            return new ArrayList<>();
        }
        return games;
    }

    @Override
    public Iterable<Game> findGamesByUserId(Long userId) {
        Iterable<Game> games;
        try {
            games = jdbcTemplate.query(SQL_FIND_GAMES_BY_USER_ID, rowMapper, userId);
        } catch (DataAccessException ex) {
            System.out.println(ex);
            return new ArrayList<>();
        }
        return games;
    }

    @Override
    public Iterable<Game> findAllById(Iterable<Long> longs) {
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
    public void delete(Game entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Game> entities) {

    }

    @Override
    public void deleteAll() {

    }


    @Override
    public void linkGameToUser(Long gameId, Long userId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_LINK_GAME_TO_USER, new String[]{"id"});

            statement.setLong(1, gameId);
            statement.setLong(2, userId);

            return statement;
        }, keyHolder);
    }

    @Override
    public Optional<Game> updateGameInfoById(Long id, String name, String description) {
        Game game = null;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_GAME_INFO_BY_ID, new String[]{"id"});

            statement.setString(1, name);
            statement.setString(2, description);
            statement.setLong(3, id);
            return statement;
        }, keyHolder);
        game.setId(keyHolder.getKey().longValue());
        return Optional.of(game);
    }


}
