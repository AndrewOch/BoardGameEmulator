package ru.kpfu.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.models.entities.Card;
import ru.kpfu.itis.repositories.interfaces.CardsRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("cardsRepositoryJdbcTemplateImpl")
public class CardsRepositoryImpl implements CardsRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Card> rowMapper = ((resultSet, rowNum) -> {
        return Card.builder()
                .id(resultSet.getLong("id"))
                .currencyId(resultSet.getLong("currency_id"))
                .deckId(resultSet.getLong("deck_id"))
                .name(resultSet.getString("title"))
                .description(resultSet.getString("description"))
                .value(resultSet.getInt("value"))
                .build();
    });

    //language=sql
    private final String SQL_INSERT_CARD = "INSERT INTO card (title, description, currency_id, \"value\", deck_id) VALUES (?,?,?,?,?)";
    private final String SQL_FIND_CARDS_BY_DECK_ID = "SELECT * FROM card WHERE deck_id=?";
    private final String SQL_FIND_ALL = "SELECT * FROM card;";
    private final String SQL_DELETE_DUPLICATES = "delete from card where ctid in(select ctid from card where uniqueness_token = ? limit ?)";
    private final String SQL_GET_DUPLICATES_COUNT = "SELECT count(*) as card_count FROM card WHERE uniqueness_token=?";
    private final String SQL_FIND_BY_ID = "SELECT * from card where id=?";
    private final String SQL_UPDATE_CARD_INFO_BY_ID = "update card set title = ?, description = ?, currency_id = ?, \"value\" = ? where id = ?";

    @Override
    public List<Card> findCardsByDeckId(Long deckId) {

        List<Card> cards;
        try {
            cards = jdbcTemplate.query(SQL_FIND_CARDS_BY_DECK_ID, rowMapper, deckId);
        } catch (DataAccessException ex) {
            return new ArrayList<>();
        }
        return cards;
    }

    @Override
    public void deleteDuplicates(Integer uniquenessToken, Integer count) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CARD_INFO_BY_ID, new String[]{"id"});

            statement.setInt(1, uniquenessToken);
            statement.setInt(2, count);

            return statement;
        }, keyHolder);
    }

    @Override
    public Integer getDuplicatesCount(Integer uniquenessToken) {
        Integer count;
        try {
            count = jdbcTemplate.queryForObject(SQL_GET_DUPLICATES_COUNT, ((resultSet, rowNum) -> resultSet.getInt("card_count")), uniquenessToken);
        } catch (DataAccessException ex) {
            return 0;
        }
        return count;
    }

    @Override
    public Optional<Card> updateCardInfoById(Long id, String name, String description, Long currencyId, Integer value) {
        Card entity = null;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_CARD_INFO_BY_ID, new String[]{"id"});

            statement.setString(1, name);
            statement.setString(2, description);
            statement.setLong(3, currencyId);
            statement.setInt(4, value);
            statement.setLong(5, id);

            return statement;
        }, keyHolder);
        entity.setId(keyHolder.getKey().longValue());
        return Optional.of(entity);
    }


    @Override
    public <S extends Card> S save(S entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_CARD, new String[]{"id"});

            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setLong(3, entity.getCurrencyId());
            statement.setInt(4, entity.getValue());
            statement.setLong(5, entity.getDeckId());

            return statement;
        }, keyHolder);
        entity.setId(keyHolder.getKey().longValue());
        return entity;
    }

    @Override
    public <S extends Card> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Card> findById(Long aLong) {
        Card card;
        try {
            card = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, rowMapper, aLong);
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
        return Optional.of(card);
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Card> findAll() {
        Iterable<Card> cards;
        try {
            cards = jdbcTemplate.query(SQL_FIND_BY_ID, rowMapper);
        } catch (DataAccessException ex) {
            return new ArrayList<>();
        }
        return cards;
    }

    @Override
    public Iterable<Card> findAllById(Iterable<Long> longs) {
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
    public void delete(Card entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Card> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
