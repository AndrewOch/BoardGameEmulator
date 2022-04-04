package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.models.entities.Deck;

import java.util.List;
import java.util.Optional;

@Repository
public interface DecksRepository extends JpaRepository<Deck, Long> {

    @Query("SELECT d.id, d.name, d.description FROM Deck d inner join GameDecks gd on d.id = gd.deckId WHERE d.id=?1")
    List<Deck> findDecksByGameId(Long gameId);

    @Query("update Deck d set d.name = ?2, d.description = ?3 where d.id = ?1")
    Optional<Deck> updateDeckInfoById(Long id, String name, String description);

    //TODO
//    void linkDeckToGame(Long deckId, Long gameId);
}