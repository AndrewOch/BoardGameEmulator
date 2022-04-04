package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.models.entities.GameDecks;
import ru.kpfu.itis.models.entities.UserGames;

@Repository
public interface GameDecksRepository extends JpaRepository<GameDecks, Long> {
}