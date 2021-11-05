package ru.kpfu.itis.repositories;

import ru.kpfu.itis.model.Deck;

import java.util.List;

public interface DecksRepository extends ru.kpfu.itis.repostories.CrudRepository<Deck> {
    List<Deck> findDecksByGameId(Long gameId);
}