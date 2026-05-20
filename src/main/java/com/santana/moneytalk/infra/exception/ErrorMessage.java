package com.santana.moneytalk.infra.exception;

import java.time.LocalDateTime;

public record ErrorMessage(String message,
                           Integer statusCode,
                           LocalDateTime dataHora) {
}
