package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.models.entities.Card;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CardsRepository extends JpaRepository<Card, Long> {

    List<Card> findAllByDeckId(Long deckId);

    @Modifying
    @Query("update Card as c set c.name = ?2, c.description = ?3, c.currencyId = ?4, c.value = ?5, c.copiesCount = ?6 where c.id = ?1")
    void updateCardInfoById(Long id, String name, String description, Long currencyId, Integer value, Integer copiesCount);

}