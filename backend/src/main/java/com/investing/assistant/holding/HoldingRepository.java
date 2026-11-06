package com.investing.assistant.holding;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Database access for holdings. Spring Data generates the implementation at startup:
 * save, findById, findAll, deleteById, etc. come from JpaRepository for free.
 */
public interface HoldingRepository extends JpaRepository<Holding, Long> {

    /** Spring builds the SQL from the method name: SELECT ... ORDER BY ticker ASC. */
    List<Holding> findAllByOrderByTickerAsc();

    /** SELECT EXISTS(... WHERE ticker = ?) */
    boolean existsByTicker(String ticker);
}
