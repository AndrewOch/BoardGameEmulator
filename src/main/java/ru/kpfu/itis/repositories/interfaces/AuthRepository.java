package ru.kpfu.itis.repositories.interfaces;

import org.springframework.data.repository.CrudRepository;
import ru.kpfu.itis.models.entities.Auth;

import java.util.Optional;

public interface AuthRepository extends CrudRepository<Auth,Long> {

    Optional<Auth> findByCookieValue(String cookieValue);
}