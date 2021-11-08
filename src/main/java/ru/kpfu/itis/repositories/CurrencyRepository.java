package ru.kpfu.itis.repositories;

import ru.kpfu.itis.model.Currency;

import java.util.List;

public interface CurrencyRepository extends ru.kpfu.itis.repostories.CrudRepository<Currency> {
    List<Currency> findCurrenciesByGameId(Long gameId);

    Currency updateCurrencyInfoById(Long id, String name, String description);

}
