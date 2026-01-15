package com.santana.moneytalk.controller;

import com.santana.moneytalk.domain.dto.response.TransacaoResponse;
import com.santana.moneytalk.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TransacaoResponse> transacoes(){
        return transacaoService.pegarTransacoes();
    }

}
