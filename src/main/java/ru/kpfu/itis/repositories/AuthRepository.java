package ru.kpfu.itis.repositories;

import ru.kpfu.itis.model.Auth;

public interface AuthRepository extends ru.kpfu.itis.repostories.CrudRepository<Auth> {
    Auth findByCookieValue(String cookieValue);
}