package com.santana.moneytalk.exception;

import java.time.LocalDateTime;

public record ErrorMessage(String message,
                           Integer statusCode,
                           LocalDateTime dataHora) {
}
