package com.santana.moneytalk.infra.exception;

public class TransacaoNotFound extends RuntimeException {
    public TransacaoNotFound() {
        super("Transacao não encontrada");
    }
}
