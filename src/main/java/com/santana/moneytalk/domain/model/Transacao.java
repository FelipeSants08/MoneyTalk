package com.santana.moneytalk.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valor;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    private LocalDate dataTransacao;

    private String descricao;

    @Override
    public String toString(){
        return  "Transacao [id=" + id + ", valor=" + valor + ", categoria=" + categoria.getNome();
    }
}
