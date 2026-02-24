package com.santana.moneytalk.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record CriarMetricaPorData(@NotNull
                                  @Past
                                  LocalDate inicio,
                                  @NotNull
                                  LocalDate fim) {
}
