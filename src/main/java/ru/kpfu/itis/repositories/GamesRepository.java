package ru.kpfu.itis.repositories;

import ru.kpfu.itis.model.Game;

import java.util.List;

public interface GamesRepository extends ru.kpfu.itis.repostories.CrudRepository<Game> {
    List<Game> findGamesByUserId(Long userId);

    void linkGameToUser(Long gameId, Long userId);

    Game updateGameInfoById(Long id, String name, String description);

}
