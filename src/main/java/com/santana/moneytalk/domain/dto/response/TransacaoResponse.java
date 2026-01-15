package com.santana.moneytalk.domain.dto.response;


import java.util.List;

public record TransacaoResponse(Long id, Double valor, CategoriaResponse categoria, String tipoTransacao, String  dataTransacao, String descricao) {
}
