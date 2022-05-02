package ru.kpfu.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.models.entities.Currency;

import java.util.List;
import java.util.Optional;

@Repository @Transactional
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    List<Currency> findAllByGameId(Long gameId);

    @Modifying
    @Query("update Currency c set c.name = ?2, c.description = ?3 where c.id = ?1")
    void updateCurrencyInfoById(Long id, String name, String description);
}
