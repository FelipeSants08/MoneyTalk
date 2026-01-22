package com.santana.moneytalk.controller;

import com.santana.moneytalk.domain.dto.request.AlteraTransacaoRequest;
import com.santana.moneytalk.domain.dto.request.TransacaoRequest;
import com.santana.moneytalk.domain.dto.response.TransacaoResponse;
import com.santana.moneytalk.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransacaoRequest criar(@RequestBody TransacaoRequest request){
        return transacaoService.criarTransacao(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable Long id, @RequestBody AlteraTransacaoRequest request){
        transacaoService.alterar(id, request);
    }

}
