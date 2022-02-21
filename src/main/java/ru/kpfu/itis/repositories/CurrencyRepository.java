package ru.kpfu.itis.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.kpfu.itis.model.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    List<Currency> findCurrenciesByGameId(Long gameId);

    Optional<Currency> updateCurrencyInfoById(Long id, String name, String description);

}
