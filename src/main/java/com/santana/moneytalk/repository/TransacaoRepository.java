package com.santana.moneytalk.repository;

import com.santana.moneytalk.domain.model.Categoria;
import com.santana.moneytalk.domain.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {


    @Query("""
        SELECT t FROM Transacao t WHERE t.dataTransacao BETWEEN :inicio AND :fim
    """)
    List<Transacao> transacoesPorDataComecoEFim(LocalDate inicio, LocalDate fim);

    @Query("""
        SELECT t.categoria.nome FROM Transacao t
        WHERE t.tipo = 'SAIDA' AND t.dataTransacao BETWEEN :inicio AND :fim
        GROUP BY t.categoria.nome
        ORDER BY SUM(t.valor) DESC LIMIT 1
    """)
    String categoriaMaisCaraNoPeriodo(LocalDate inicio, LocalDate fim);
}
