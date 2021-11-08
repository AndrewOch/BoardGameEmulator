package ru.kpfu.itis.repositories;

import ru.kpfu.itis.model.Card;

import java.util.List;

public interface CardsRepository extends ru.kpfu.itis.repostories.CrudRepository<Card> {
    List<Card> findCardsByDeckId(Long deckId);

    void deleteDuplicates(Integer uniquenessToken, Integer count);

    Integer getDuplicatesCount(Integer uniquenessToken);

    Card updateCardInfoById(Long id, String name, String description, Long currencyId, Integer value);

}