package com.santana.moneytalk.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AlteraTransacaoRequest(@Min(message = "Valor deve ser positivo", value = 1) Double valor, @PastOrPresent LocalDate dataTransacao, @Size(min = 4, message = "Minimo 4 caracteres na descrição") String descricao) {
}
