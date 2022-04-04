package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.models.entities.Card;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardsRepository extends JpaRepository<Card, Long> {

    List<Card> findAllByDeckId(Long deckId);

    //TODO
    //@Query("delete from Card as c where ctid in((select ctid from Card as c1 where c1.uniquenessToken = ?1) limit ?2))")
    //void deleteDuplicates(Integer uniquenessToken, Integer count);

    @Query("SELECT count(c) as card_count FROM Card as c WHERE c.uniquenessToken=?1")
    Integer getDuplicatesCount(Integer uniquenessToken);

    @Query("update Card as c set c.name = ?2, c.description = ?3, c.currencyId = ?4, c.value = ?5 where c.id = ?1")
    Optional<Card> updateCardInfoById(Long id, String name, String description, Long currencyId, Integer value);

}