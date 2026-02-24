package com.santana.moneytalk.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "metricas")
@Getter
@Setter
public class Metrica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataReferencia;

    private Integer quantidadeTransacao;

    private Double saldo;

    private Double media;

    private Double totalEntradas;

    private Double totalSaida;

    @Column(length = 1000)
    private String analiseIa;

    private String categoriaMaisCara;

    public Metrica() {
    }

    public Metrica(Integer quantidadeTransacao, Double saldo, Double media, Double totalEntradas, Double totalSaida, String analiseIa, String categoriaMaisCara) {
        this.dataReferencia = LocalDate.now();
        this.quantidadeTransacao = quantidadeTransacao;
        this.saldo = saldo;
        this.media = media;
        this.totalEntradas = totalEntradas;
        this.totalSaida = totalSaida;
        this.analiseIa = analiseIa;
        this.categoriaMaisCara = categoriaMaisCara;
    }
}
