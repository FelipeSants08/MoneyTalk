package com.santana.moneytalk.domain.dto.response;

import com.santana.moneytalk.domain.model.Metrica;

import java.time.LocalDate;

public record MetricaResponse(Long id, LocalDate dataReferencia, Integer quantidadeTransacao, Double saldo, Double media,
                              Double totalEntradas, Double totalSaida, String analiseIa, String categoriaMaisCara) {
    public MetricaResponse(Metrica metrica){
        this(metrica.getId(), metrica.getDataReferencia(), metrica.getQuantidadeTransacao(),
                metrica.getSaldo(), metrica.getMedia(), metrica.getTotalEntradas(), metrica.getTotalSaida(),
                metrica.getAnaliseIa(), metrica.getCategoriaMaisCara());
    }
}
