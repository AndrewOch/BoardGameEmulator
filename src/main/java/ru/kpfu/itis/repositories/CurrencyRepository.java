package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.models.entities.Currency;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    List<Currency> findAllByGameId(Long gameId);

    @Query("update Currency c set c.name = ?2, c.description = ?3 where c.id = ?1")
    Optional<Currency> updateCurrencyInfoById(Long id, String name, String description);
}
