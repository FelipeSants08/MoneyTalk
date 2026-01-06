package com.santana.moneytalk.domain.dto.response;

import com.santana.moneytalk.domain.model.TipoTransacao;

import java.time.LocalDate;

public record TransacaoResponse(Double valor, String nomeCategoria, String tipoTransacao, String  dataTransacao, String descricao) {
}
