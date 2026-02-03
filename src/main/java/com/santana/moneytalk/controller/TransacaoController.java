package com.santana.moneytalk.controller;

import com.santana.moneytalk.docs.ITransacaoController;
import com.santana.moneytalk.domain.dto.request.AlteraTransacaoRequest;
import com.santana.moneytalk.domain.dto.request.CategoriaRequest;
import com.santana.moneytalk.domain.dto.request.TransacaoRequest;
import com.santana.moneytalk.domain.dto.response.TransacaoResponse;
import com.santana.moneytalk.service.TransacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transacoes")
public class TransacaoController implements ITransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @GetMapping
    @Cacheable("transacoes")
    @ResponseStatus(HttpStatus.OK)
    public List<TransacaoResponse> transacoes(){
        //transacaoService.estatisticas();
        return transacaoService.pegarTransacoes();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransacaoResponse criar(@Valid @RequestBody TransacaoRequest request){
        return transacaoService.criarTransacao(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void atualizar(@PathVariable Long id, @RequestBody AlteraTransacaoRequest request){
        transacaoService.alterar(id, request);
    }

    @PutMapping("{id}/categoria")
    public void atualizarCategoria(Long id, CategoriaRequest request) {
        transacaoService.alterarCategoria(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id){
        transacaoService.deletarTransacao(id);
    }

}
