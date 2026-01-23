package com.santana.moneytalk.exception;

public class TransacaoNotFound extends RuntimeException {
    public TransacaoNotFound() {
        super("Transacao não encontrada");
    }
}
