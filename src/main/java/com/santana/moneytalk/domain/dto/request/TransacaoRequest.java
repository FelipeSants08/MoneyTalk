package com.santana.moneytalk.domain.dto.request;

import com.santana.moneytalk.domain.model.Categoria;
import com.santana.moneytalk.domain.model.TipoTransacao;

import java.time.LocalDate;

public record TransacaoRequest(Double valor, CategoriaRequest categoria, TipoTransacao tipo, LocalDate dataTransacao, String descricao) {
}
