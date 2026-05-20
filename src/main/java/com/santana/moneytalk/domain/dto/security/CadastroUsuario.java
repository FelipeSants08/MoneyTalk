package com.santana.moneytalk.domain.dto.security;

import jakarta.validation.constraints.NotBlank;

public record CadastroUsuario(@NotBlank String nome,
                              @NotBlank String login,
                              @NotBlank String senha) {
}
