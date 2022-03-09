package ru.kpfu.itis.repositories.interfaces;

import org.springframework.data.repository.CrudRepository;
import ru.kpfu.itis.models.entities.Game;

import java.util.Optional;

public interface GamesRepository extends CrudRepository<Game,Long> {
    Iterable<Game> findGamesByUserId(Long userId);

    void linkGameToUser(Long gameId, Long userId);

    Optional<Game> updateGameInfoById(Long id, String name, String description);

}
