package com.santana.moneytalk.service;

import com.santana.moneytalk.domain.dto.request.CategoriaRequest;
import com.santana.moneytalk.domain.dto.request.TransacaoRequest;
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

        Categoria categoria = buscarOuCriarCategoria(request.categoria());

        Transacao transacao = mapearTrasacao(request, categoria);
        transacaoService.salvar(transacao);
    }

    public List<TransacaoResponse> pegarTransacoesAnalise(){
        List<Transacao> transacoes = transacaoService.findAll();
        return transacoes.stream()
                .map(t -> new TransacaoResponse(
                        t.getValor(),
                        t.getCategoria().getNome(),
                        t.getTipo().toString(),
                        t.getDataTransacao().toString(),
                        t.getDescricao()
                )).toList();
    }


    private Categoria buscarOuCriarCategoria(CategoriaRequest request){
        return categoriaService.pegarPorNome(request.nome())
                .orElseGet(() -> categoriaService.save(request));
    }

    private Transacao mapearTrasacao(TransacaoRequest request, Categoria categoria){
        Transacao transacao = new Transacao();
        transacao.setCategoria(categoria);
        transacao.setDataTransacao(request.dataTransacao());
        transacao.setValor(request.valor());
        transacao.setDescricao(request.descricao());
        transacao.setTipo(request.tipo());
        return transacao;
    }

}
