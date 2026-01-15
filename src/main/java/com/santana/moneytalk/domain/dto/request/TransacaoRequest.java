package com.santana.moneytalk.domain.dto.request;

import com.santana.moneytalk.domain.model.Categoria;
import com.santana.moneytalk.domain.model.TipoTransacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TransacaoRequest(@NotNull(message = "Campo obrigatório") Double valor,
                               @NotNull(message = "Campo obrigatório") CategoriaRequest categoria,
                               TipoTransacao tipo,
                               @NotNull(message = "Campo obrigatório") LocalDate dataTransacao,
                               @NotBlank(message = "Campo obrigatório") String descricao) {
}
