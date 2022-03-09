package ru.kpfu.itis.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.models.entities.User;
import ru.kpfu.itis.repositories.interfaces.UsersRepository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Optional;

@Component("usersRepositoryJdbcTemplateImpl")
public class UsersRepositoryImpl implements UsersRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> userRowMapper = ((resultSet, rowNum) -> {
        return User.builder()
                .id(resultSet.getLong("id"))
                .username(resultSet.getString("username"))
                .email(resultSet.getString("email"))
                .passwordHash(resultSet.getString("password_hash"))
//                .createdAt(Timestamp.valueOf(resultSet.getString("created_at")))
                .build();
    });

    //language=sql
    private final String SQL_INSERT_USER = "INSERT INTO users(username, email, password_hash) VALUES (?, ?, ?)";
    private final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM users WHERE username=?";
    private final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";

    @Override
    public Iterable<User> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public <S extends User> S save(S user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER, new String[]{"id"});

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPasswordHash());

            return statement;
        }, keyHolder);
        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {


        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        User user;
        try {
            user = jdbcTemplate.queryForObject(SQL_FIND_USER_BY_ID, userRowMapper, aLong);
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByLogin(String username) {
        User user;
        try {
            user = jdbcTemplate.queryForObject(SQL_FIND_USER_BY_LOGIN, userRowMapper, username);
        } catch (DataAccessException ex) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }

}