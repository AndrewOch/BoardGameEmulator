package ru.kpfu.itis.repositories;

import ru.kpfu.itis.mapper.RowMapper;
import ru.kpfu.itis.model.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CurrencyRepositoryImpl implements CurrencyRepository {

    private Connection connection;

    //language=sql
    private final String INSERT_CURRENCY = "INSERT INTO currency (currency_name, currency_description, game_id) VALUES (?,?,?)";
    private final String FIND_CURRENCIES_BY_GAME_ID = "SELECT * FROM currency WHERE game_id=?";
    private final String FIND_ALL = "SELECT * FROM currency;";

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
        return Optional.empty();
    }

    @Override
    public Currency save(Currency currency) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Currency> findCurrenciesByGameId(Long gameId) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_CURRENCIES_BY_GAME_ID);
            preparedStatement.setLong(1, gameId);
            resultSet = preparedStatement.executeQuery();
            List<Currency> currencies = rowMapGames.rowMap(resultSet);
            return currencies;
        } catch (SQLException e) {
        }
        return null;
    }


    private RowMapper<Currency> rowMapper = ((resultSet) -> {
        if (resultSet.next()) {
            Currency currency = new Currency();
            currency.setId(resultSet.getLong("id"));
            currency.setName(resultSet.getString("game_name"));
            currency.setDescription(resultSet.getString("game_description"));
            return currency;
        } else {
            return null;
        }
    });

    private RowMapper<List<Currency>> rowMapGames = ((resultSet) -> {
        List<Currency> products = new ArrayList<>();
        while (resultSet.next()) {
            Currency currency = new Currency();
            currency.setId(resultSet.getLong("id"));
            currency.setName(resultSet.getString("game_name"));
            currency.setDescription(resultSet.getString("game_description"));
            products.add(currency);
        }
        return products;
    });
}
