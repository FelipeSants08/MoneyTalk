package com.santana.moneytalk.repository;

import com.santana.moneytalk.domain.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query(
            """
        select c from Categoria c where c.nome = :nome
     """
    )
    Optional<Categoria> pegarPorNome(@Param("nome") String nome);

}
