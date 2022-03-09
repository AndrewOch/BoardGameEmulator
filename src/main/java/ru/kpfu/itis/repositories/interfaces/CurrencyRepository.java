package ru.kpfu.itis.repositories.interfaces;

import org.springframework.data.repository.CrudRepository;
import ru.kpfu.itis.models.entities.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository extends CrudRepository<Currency, Long> {
    List<Currency> findCurrenciesByGameId(Long gameId);

    Optional<Currency> updateCurrencyInfoById(Long id, String name, String description);

}
