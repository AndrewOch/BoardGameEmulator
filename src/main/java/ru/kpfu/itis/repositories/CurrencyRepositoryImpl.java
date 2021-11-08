package ru.kpfu.itis.repositories;

import ru.kpfu.itis.mapper.RowMapper;
import ru.kpfu.itis.model.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyRepositoryImpl implements CurrencyRepository {

    private Connection connection;

    //language=sql
    private final String INSERT_CURRENCY = "INSERT INTO currency (currency_name, currency_description, game_id) VALUES (?,?,?)";
    private final String FIND_CURRENCIES_BY_GAME_ID = "SELECT * FROM currency WHERE game_id=?";
    private final String FIND_ALL = "SELECT * FROM currency;";
    private final String FIND_BY_ID = "SELECT * FROM currency WHERE id=?";
    private final String UPDATE_CURRENCY_INFO_BY_ID = "update currency set currency_name = ?, currency_description = ? where id = ?;";


    public CurrencyRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Currency> findAll() {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL);
            resultSet = preparedStatement.executeQuery();
            List<Currency> currencies = rowMapGames.rowMap(resultSet);
            return currencies;
        } catch (SQLException e) {

        }
        return null;
    }

    @Override
    public Optional<Currency> findById(Long id) {
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
    public Currency save(Currency currency) {

        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CURRENCY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, currency.getName());
            preparedStatement.setString(2, currency.getDescription());
            preparedStatement.setLong(3, currency.getGameId());
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
    public List<Currency> findCurrenciesByGameId(Long gameId) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_CURRENCIES_BY_GAME_ID, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, gameId);
            resultSet = preparedStatement.executeQuery();
            return rowMapGames.rowMap(resultSet);
        } catch (SQLException e) {
        }
        return new ArrayList<>();
    }

    @Override
    public Currency updateCurrencyInfoById(Long id, String name, String description) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CURRENCY_INFO_BY_ID, Statement.RETURN_GENERATED_KEYS);
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


    private RowMapper<Currency> rowMapper = ((resultSet) -> {
        if (resultSet.next()) {
            Currency currency = new Currency();
            currency.setId(resultSet.getLong("id"));
            currency.setName(resultSet.getString("currency_name"));
            currency.setDescription(resultSet.getString("currency_description"));
            currency.setCreatedAt(resultSet.getTimestamp("created_at"));
            currency.setGameId(resultSet.getLong("game_id"));
            return currency;
        } else {
            return null;
        }
    });

    private RowMapper<List<Currency>> rowMapGames = ((resultSet) -> {
        List<Currency> currencies = new ArrayList<>();
        while (resultSet.next()) {
            Currency currency = new Currency();
            currency.setId(resultSet.getLong("id"));
            currency.setName(resultSet.getString("currency_name"));
            currency.setDescription(resultSet.getString("currency_description"));
            currency.setCreatedAt(resultSet.getTimestamp("created_at"));
            currency.setGameId(resultSet.getLong("game_id"));
            currencies.add(currency);
        }
        return currencies;
    });
}
