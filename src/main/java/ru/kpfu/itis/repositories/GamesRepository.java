package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.models.entities.Game;
import ru.kpfu.itis.models.entities.User;

import java.util.List;
import java.util.Optional;

@Repository @Transactional
public interface GamesRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g.id, g.name, g.description FROM Game g INNER JOIN UserGames u_g ON g.id = u_g.gameId INNER JOIN User u ON u_g.userId = u.id WHERE u.id=?1")
    List<Object[]> findAllByUserId(Long userId);

    @Modifying
    @Query("update Game as g set g.name = ?2, g.description = ?3 where g.id = ?1")
    void updateGameInfoById(Long id, String name, String description);
}
