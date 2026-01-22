package com.santana.moneytalk.domain.dto.request;

import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record AlteraTransacaoRequest(Double valor, @PastOrPresent LocalDate dataTransacao, String descricao) {
}
