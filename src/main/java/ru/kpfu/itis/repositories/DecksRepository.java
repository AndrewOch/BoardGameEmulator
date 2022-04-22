package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.models.entities.Deck;

import java.util.List;
import java.util.Optional;

@Repository @Transactional
public interface DecksRepository extends JpaRepository<Deck, Long> {

    @Query("SELECT d.id, d.name, d.description FROM Deck d inner join GameDecks gd on d.id = gd.deckId WHERE d.id=?1")
    List<Object[]> findDecksByGameId(Long gameId);

    @Modifying
    @Query("update Deck d set d.name = ?2, d.description = ?3 where d.id = ?1")
    void updateDeckInfoById(Long id, String name, String description);
}