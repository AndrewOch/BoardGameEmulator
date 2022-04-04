package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.models.entities.User;
import ru.kpfu.itis.models.entities.UserGames;

import java.util.Optional;

@Repository
public interface UserGamesRepository extends JpaRepository<UserGames, Long> {
}