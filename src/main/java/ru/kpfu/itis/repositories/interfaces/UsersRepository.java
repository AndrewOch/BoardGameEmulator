package ru.kpfu.itis.repositories.interfaces;

import org.springframework.data.repository.CrudRepository;
import ru.kpfu.itis.models.entities.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);
}