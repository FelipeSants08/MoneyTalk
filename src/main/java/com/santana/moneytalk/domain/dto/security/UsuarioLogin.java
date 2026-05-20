package com.santana.moneytalk.domain.dto.security;

import jakarta.validation.constraints.NotBlank;

public record UsuarioLogin(@NotBlank String login,
                           @NotBlank String senha) {
}
