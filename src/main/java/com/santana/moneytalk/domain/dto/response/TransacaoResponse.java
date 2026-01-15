package com.santana.moneytalk.domain.dto.response;


import com.santana.moneytalk.domain.model.TipoTransacao;

import java.time.LocalDate;
import java.util.List;

public record TransacaoResponse(Long id, Double valor, CategoriaResponse categoria, TipoTransacao tipoTransacao, LocalDate dataTransacao, String descricao) {
}
