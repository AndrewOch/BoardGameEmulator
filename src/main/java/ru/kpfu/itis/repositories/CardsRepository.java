package ru.kpfu.itis.repositories;

import ru.kpfu.itis.model.Card;

import java.util.List;

public interface CardsRepository extends ru.kpfu.itis.repostories.CrudRepository<Card> {
    List<Card> findCardsByDeckId(Long deckId);
}