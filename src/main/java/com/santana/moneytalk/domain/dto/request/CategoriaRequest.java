package com.santana.moneytalk.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequest(@NotBlank(message = "Campo obrigatório")
                               @Size(max = 10, min = 3, message = "Número de caracteres inválido (3 - 10)")
                               String nome) {
}
