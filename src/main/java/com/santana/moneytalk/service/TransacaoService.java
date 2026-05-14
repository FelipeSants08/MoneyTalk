package com.santana.moneytalk.service;

import com.santana.moneytalk.domain.dto.request.AlteraTransacaoRequest;
import com.santana.moneytalk.domain.dto.request.CategoriaRequest;
import com.santana.moneytalk.domain.dto.request.TransacaoRequest;
import com.santana.moneytalk.domain.dto.response.TransacaoResponse;
import com.santana.moneytalk.domain.model.Categoria;
import com.santana.moneytalk.domain.model.Transacao;
import com.santana.moneytalk.exception.TransacaoNotFound;
import com.santana.moneytalk.mapper.Mappers;
import com.santana.moneytalk.repository.TransacaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {

    private final TransacaoRepository repository;
    private final CategoriaService categoriaService;




    @Transactional
    public TransacaoResponse criarTransacao(TransacaoRequest req){
        Categoria categoria = categoriaService.buscarOuCriarCategoria(req.categoria());

        Transacao transacao = Mappers.toTransacao(req);

        transacao.setCategoria(categoria);

        log.info("Salvando a transacao {}", transacao.getDescricao());

        repository.save(transacao);

        return Mappers.toTransacaoResponse(transacao);
    }

    @Transactional
    public void alterarCategoria(Long id, CategoriaRequest request){
        Transacao transacao = findById(id);
        Categoria categoria = categoriaService.buscarOuCriarCategoria(request);
        transacao.setCategoria(categoria);
        repository.save(transacao);
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

    public void deletarTransacao(Long id){
        repository.delete(findById(id));
    }

    public Transacao findById(Long id){
        return repository.findById(id).orElseThrow(
                TransacaoNotFound::new
        );
    }



}
