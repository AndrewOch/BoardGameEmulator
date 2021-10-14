package ru.kpfu.itis.repositories;

import ru.kpfu.itis.models.Auth;

public interface AuthRepository extends ru.kpfu.itis.repostories.CrudRepository<Auth> {
    Auth findByCookieValue(String cookieValue);
}