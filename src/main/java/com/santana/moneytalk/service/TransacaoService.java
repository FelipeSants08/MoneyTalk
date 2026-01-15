package com.santana.moneytalk.service;

import com.santana.moneytalk.domain.dto.request.AlteraTransacao;
import com.santana.moneytalk.domain.dto.request.TransacaoRequest;
import com.santana.moneytalk.domain.dto.response.CategoriaResponse;
import com.santana.moneytalk.domain.dto.response.TransacaoResponse;
import com.santana.moneytalk.domain.model.Categoria;
import com.santana.moneytalk.domain.model.Transacao;
import com.santana.moneytalk.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository repository;


    public void salvar(Transacao transacao){
        repository.save(transacao);
    }

    public void alterar(Long id, AlteraTransacao transacao){
        var transacaoEntity = findById(id);
        transacaoEntity = Transacao.builder()
                .valor(transacao.valor() != null ? transacao.valor() : transacaoEntity.getValor())
                .descricao(transacao.descricao() != null ? transacao.descricao() : transacaoEntity.getDescricao())
                .dataTransacao(transacao.dataTransacao() != null ? transacao.dataTransacao() : transacaoEntity.getDataTransacao())
                .build();
        repository.save(transacaoEntity);
    }

    public List<TransacaoResponse> pegarTransacoes(){
        List<Transacao> transacoes = repository.findAll();
        return transacoes.stream()
                .map(t -> new TransacaoResponse(
                        t.getId(),
                        t.getValor(),
                        new CategoriaResponse(t.getCategoria().getNome()),
                        t.getTipo().toString(),
                        t.getDataTransacao().toString(),
                        t.getDescricao()
                )).toList();
    }

    public Transacao mapearTrasacao(TransacaoRequest request, Categoria categoria){
        Transacao transacao = new Transacao(request, categoria);
        return transacao;
    }

    public Transacao findById(Long id){
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Transacao não encontrada")
        );
    }

}
