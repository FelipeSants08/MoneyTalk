package com.santana.moneytalk.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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

    private Double mediaSaida;

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
        this.mediaSaida = media;
        this.totalEntradas = totalEntradas;
        this.totalSaida = totalSaida;
        this.analiseIa = analiseIa;
        this.categoriaMaisCara = categoriaMaisCara;
    }

    public Metrica(List<Transacao> transacoes, String analiseIa, String categoriaMaisCara) {
        this.dataReferencia = LocalDate.now();
        this.quantidadeTransacao = transacoes.size();
        this.totalEntradas = calcularTotal(transacoes, TipoTransacao.ENTRADA);
        this.totalSaida = calcularTotal(transacoes, TipoTransacao.SAIDA);
        this.saldo = this.totalEntradas - this.totalSaida;
        this.mediaSaida = transacoes.stream().filter(t -> t.getTipo() == TipoTransacao.SAIDA)
                .mapToDouble(Transacao::getValor).average().orElse(0.0);
        this.analiseIa = analiseIa;
        this.categoriaMaisCara = categoriaMaisCara;
    }

    private Double calcularTotal(List<Transacao> transacoes, TipoTransacao tipoTransacao) {
        return transacoes.stream()
                .filter(t -> t.getTipo() == tipoTransacao)
                .mapToDouble(Transacao::getValor)
                .sum();
    }
}
