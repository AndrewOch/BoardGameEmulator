package ru.kpfu.itis.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.kpfu.itis.model.Deck;

import java.util.List;
import java.util.Optional;

public interface DecksRepository extends CrudRepository<Deck,Long> {
    List<Deck> findDecksByGameId(Long gameId);

    Optional<Deck> updateDeckInfoById(Long id, String name, String description);

    void linkDeckToGame(Long deckId, Long gameId);
}