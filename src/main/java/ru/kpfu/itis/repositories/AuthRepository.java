package ru.kpfu.itis.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.kpfu.itis.model.Auth;

import java.util.Optional;

public interface AuthRepository extends CrudRepository<Auth,Long> {

    Optional<Auth> findByCookieValue(String cookieValue);
}