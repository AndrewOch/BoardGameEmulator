package ru.kpfu.itis.repositories;

import ru.kpfu.itis.mapper.RowMapper;
import ru.kpfu.itis.model.Card;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardsRepositoryImpl implements CardsRepository {

    private Connection connection;

    //language=sql
    private final String INSERT_CARD = "INSERT INTO card (title, description, currency_id, \"value\", deck_id) VALUES (?,?,?,?,?)";
    private final String FIND_CARDS_BY_DECK_ID = "SELECT * FROM card WHERE deck_id=?";
    private final String FIND_ALL = "SELECT * FROM card;";
    private final String DELETE_DUPLICATES = "delete from card where ctid in(select ctid from card where uniqueness_token = ? limit ?)";
    private final String GET_DUPLICATES_COUNT = "SELECT count(*) FROM card WHERE uniqueness_token=?";
    private final String FIND_BY_ID = "SELECT * from card where id=?";
    private final String UPDATE_CARD_INFO_BY_ID = "update card set title = ?, description = ?, currency_id = ?, \"value\" = ? where id = ?";


    public CardsRepositoryImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Card> findCardsByDeckId(Long deckId) {

        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_CARDS_BY_DECK_ID, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, deckId);
            resultSet = preparedStatement.executeQuery();
            return rowMapGames.rowMap(resultSet);
        } catch (SQLException e) {
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteDuplicates(Integer uniquenessToken, Integer count) {

        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_DUPLICATES);
            preparedStatement.setInt(1, uniquenessToken);
            preparedStatement.setInt(2, count);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
        }
    }

    @Override
    public Integer getDuplicatesCount(Integer uniquenessToken) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_DUPLICATES_COUNT);
            preparedStatement.setInt(1, uniquenessToken);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
        }
        return null;
    }

    @Override
    public Card updateCardInfoById(Long id, String name, String description, Long currencyId, Integer value) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CARD_INFO_BY_ID, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setLong(3, currencyId);
            preparedStatement.setInt(4, value);
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            return rowMapper.rowMap(resultSet);

        } catch (SQLException e) {

        }
        return null;
    }

    @Override
    public List<Card> findAll() {
        return null;
    }

    @Override
    public Optional<Card> findById(Long id) {
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
    public Card save(Card card) {

        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CARD, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, card.getName());
            preparedStatement.setString(2, card.getDescription());
            preparedStatement.setLong(3, card.getCurrencyId());
            preparedStatement.setInt(4, card.getValue());
            preparedStatement.setLong(5, card.getDeckId());
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


    private RowMapper<Card> rowMapper = ((resultSet) -> {
        if (resultSet.next()) {
            Card card = new Card();
            card.setId(resultSet.getLong("id"));
            card.setCurrencyId(resultSet.getLong("currency_id"));
            card.setDeckId(resultSet.getLong("deck_id"));
            card.setName(resultSet.getString("title"));
            card.setDescription(resultSet.getString("description"));
            card.setValue(resultSet.getInt("value"));
            return card;
        } else {
            return null;
        }
    });

    private RowMapper<List<Card>> rowMapGames = ((resultSet) -> {
        List<Card> cards = new ArrayList<>();
        while (resultSet.next()) {
            Card card = new Card();
            card.setId(resultSet.getLong("id"));
            card.setCurrencyId(resultSet.getLong("currency_id"));
            card.setDeckId(resultSet.getLong("deck_id"));
            card.setName(resultSet.getString("title"));
            card.setDescription(resultSet.getString("description"));
            card.setValue(resultSet.getInt("value"));
            cards.add(card);
        }
        return cards;
    });
}
