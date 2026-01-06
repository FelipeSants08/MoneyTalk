package com.santana.moneytalk.service;

import com.santana.moneytalk.domain.dto.request.TransacaoRequest;
import com.santana.moneytalk.domain.model.Transacao;
import com.santana.moneytalk.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository repository;

    public List<Transacao> findAll(){
        return repository.findAll();
    }

    public void salvar(Transacao transacao){
        repository.save(transacao);
    }

    public void alterar(Long id, TransacaoRequest transacao){
        var transacaoEntity = findById(id);
        transacaoEntity.setValor(transacao.valor());
        transacaoEntity.setDescricao(transacao.descricao());
        //transacaoEntity.setCategoria(transacao.categoria());
        transacaoEntity.setTipo(transacao.tipo());
        transacaoEntity.setDataTransacao(transacao.dataTransacao());
        repository.save(transacaoEntity);
    }

    public Transacao findById(Long id){
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Transacao não encontrada")
        );
    }

}
