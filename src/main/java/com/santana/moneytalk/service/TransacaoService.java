package com.santana.moneytalk.service;

import com.santana.moneytalk.domain.dto.request.AlteraTransacaoRequest;
import com.santana.moneytalk.domain.dto.request.TransacaoRequest;
import com.santana.moneytalk.domain.dto.response.TransacaoResponse;
import com.santana.moneytalk.domain.model.Categoria;
import com.santana.moneytalk.domain.model.Transacao;
import com.santana.moneytalk.mapper.Mappers;
import com.santana.moneytalk.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository repository;
    private final CategoriaService categoriaService;


    public void salvar(Transacao transacao){
        repository.save(transacao);
    }

    @Transactional
    public TransacaoRequest criarTransacao(TransacaoRequest req){
        Categoria categoria = categoriaService.buscarOuCriarCategoria(req.categoria());
        Transacao transacao = Mappers.toTransacao(req);
        transacao.setCategoria(categoria);
        salvar(transacao);
        return req;
    }

    public void alterar(Long id, AlteraTransacaoRequest transacao){
        var transacaoExistente = findById(id);
        var transacaoAtualizada = Transacao.builder()
                .id(id)
                .valor(transacao.valor() != null ? transacao.valor() : transacaoExistente.getValor())
                .descricao(transacao.descricao() != null ? transacao.descricao() : transacaoExistente.getDescricao())
                .dataTransacao(transacao.dataTransacao() != null ? transacao.dataTransacao() : transacaoExistente.getDataTransacao())
                .categoria(transacaoExistente.getCategoria())
                .tipo(transacaoExistente.getTipo())
                .build();
        repository.save(transacaoAtualizada);
    }

    public List<TransacaoResponse> pegarTransacoes(){
        List<Transacao> transacoes = repository.findAll();
        return transacoes.stream()
                .map(Mappers::toTransacaoResponse)
                .toList();
    }


    public Transacao findById(Long id){
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("Transacao não encontrada")
        );
    }

}
