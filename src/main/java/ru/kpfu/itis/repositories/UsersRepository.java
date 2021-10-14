package ru.kpfu.itis.repositories;

import ru.kpfu.itis.model.User;

public interface UsersRepository extends ru.kpfu.itis.repostories.CrudRepository<User> {
    User findByLogin(String login);
}