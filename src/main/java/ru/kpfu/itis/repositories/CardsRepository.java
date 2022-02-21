package ru.kpfu.itis.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.kpfu.itis.model.Card;

import java.util.List;
import java.util.Optional;

public interface CardsRepository extends CrudRepository<Card, Long> {
    List<Card> findCardsByDeckId(Long deckId);

    void deleteDuplicates(Integer uniquenessToken, Integer count);

    Integer getDuplicatesCount(Integer uniquenessToken);

    Optional<Card> updateCardInfoById(Long id, String name, String description, Long currencyId, Integer value);

}