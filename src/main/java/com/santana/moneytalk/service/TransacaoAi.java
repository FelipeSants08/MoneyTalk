package com.santana.moneytalk.service;

import com.santana.moneytalk.domain.dto.request.TransacaoRequest;
import com.santana.moneytalk.domain.dto.response.CategoriaResponse;
import com.santana.moneytalk.domain.dto.response.TransacaoResponse;
import com.santana.moneytalk.domain.model.Categoria;
import com.santana.moneytalk.domain.model.Transacao;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TransacaoAi {

    private final TransacaoService transacaoService;
    private final CategoriaService categoriaService;

    public void processarESalvar(TransacaoRequest request){

        Categoria categoria = categoriaService.buscarOuCriarCategoria(request.categoria());

        Transacao transacao = transacaoService.mapearTrasacao(request, categoria);
        transacaoService.salvar(transacao);
    }


}
