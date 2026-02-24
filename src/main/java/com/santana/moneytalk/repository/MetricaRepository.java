package com.santana.moneytalk.repository;

import com.santana.moneytalk.domain.model.Metrica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MetricaRepository extends JpaRepository<Metrica, Long> {

    @Query("""
        SELECT m FROM Metrica m ORDER BY m.id DESC LIMIT 1
    """)
    Optional<Metrica> pegarUltimaMetrica();

}
