package ru.kpfu.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.models.entities.Currency;
import ru.kpfu.itis.repositories.interfaces.CurrencyRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("currencyRepositoryJdbcTemplateImpl")
public class CurrencyRepositoryImpl implements CurrencyRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Currency> rowMapper = ((resultSet, rowNum) -> {
        return Currency.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("currency_name"))
                .description(resultSet.getString("currency_description"))
                .createdAt(resultSet.getTimestamp("created_at"))
                .gameId(resultSet.getLong("game_id"))
                .build();
    });

    //language=sql
    private final String SQL_INSERT_CURRENCY = "INSERT INTO currency (currency_name, currency_description, game_id) VALUES (?,?,?)";
    private final String SQL_FIND_CURRENCIES_BY_GAME_ID = "SELECT * FROM currency WHERE game_id=?";
    private final String SQL_FIND_ALL = "SELECT * FROM currency;";
    private final String SQL_FIND_BY_ID = "SELECT * FROM currency WHERE id=?";
    private final String SQL_UPDATE_CURRENCY_INFO_BY_ID = "update currency set currency_name = ?, currency_description = ? where id = ?;";


    @Override
    public <S extends Currency> S save(S entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_CURRENCY, new String[]{"id"});

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setLong(3, entity.getGameId());

            return statement;
        }, keyHolder);
        entity.setId(keyHolder.getKey().longValue());
        return entity;
    }

    @Override
    public <S extends Currency> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Currency> findById(Long aLong) {
        Currency currency;
        try {
            currency = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, rowMapper, aLong);
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
        return Optional.of(currency);
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Currency> findAll() {
        Iterable<Currency> currencies;
        try {
            currencies = jdbcTemplate.query(SQL_FIND_ALL, rowMapper);
        } catch (DataAccessException ex) {
            return new ArrayList<>();
        }
        return currencies;
    }

    @Override
    public Iterable<Currency> findAllById(Iterable<Long> longs) {
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
    public void delete(Currency entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Currency> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Currency> findCurrenciesByGameId(Long gameId) {

        List<Currency> currencies;
        try {
            currencies = jdbcTemplate.query(SQL_FIND_CURRENCIES_BY_GAME_ID, rowMapper, gameId);
        } catch (DataAccessException ex) {
            return new ArrayList<>();
        }
        return currencies;
    }

    @Override
    public Optional<Currency> updateCurrencyInfoById(Long id, String name, String description) {
        Currency currency = null;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CURRENCY_INFO_BY_ID, new String[]{"id"});

            statement.setString(1, name);
            statement.setString(2, description);
            statement.setLong(3, id);
            return statement;
        }, keyHolder);
        currency.setId(keyHolder.getKey().longValue());
        return Optional.of(currency);
    }
}
