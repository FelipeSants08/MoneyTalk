package com.santana.moneytalk.domain.dto.request;

import com.santana.moneytalk.domain.model.Categoria;
import com.santana.moneytalk.domain.model.TipoTransacao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record TransacaoRequest(@NotNull(message = "Campo obrigatório")@Min(message = "Valor tem que ser positivo", value = 1)
                               Double valor,
                               @NotNull(message = "Campo obrigatório") @Valid
                               CategoriaRequest categoria,
                               @NotNull(message = "Campo obrigatório")
                               TipoTransacao tipo,
                               @NotNull(message = "Campo obrigatório")@PastOrPresent(message = "Transacao não pode estar no futuro")
                               LocalDate dataTransacao,
                               @NotBlank(message = "Campo obrigatório")@Size(min = 4, message = "Descrição deve ser maior que 4 caracteres")
                               String descricao) {
}
