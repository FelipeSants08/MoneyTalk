package com.santana.moneytalk.mapper;

import com.santana.moneytalk.domain.dto.request.CategoriaRequest;
import com.santana.moneytalk.domain.dto.request.TransacaoRequest;
import com.santana.moneytalk.domain.dto.response.CategoriaResponse;
import com.santana.moneytalk.domain.dto.response.TransacaoResponse;
import com.santana.moneytalk.domain.model.Categoria;
import com.santana.moneytalk.domain.model.Transacao;

public class Mappers {



    public static Transacao toTransacao(TransacaoRequest req){
        Transacao transacao = Transacao.builder()
                .valor(req.valor())
                .dataTransacao(req.dataTransacao())
                .descricao(req.descricao())
                .tipo(req.tipo())
                .categoria(toCategoria(req.categoria()))
                .build();
        return transacao;
    }

    public static TransacaoResponse toTransacaoResponse(Transacao transacao){
        TransacaoResponse req = new TransacaoResponse(
                transacao.getId(),
                transacao.getValor(),
                toCategoriaResponse(transacao.getCategoria()),
                transacao.getTipo(),
                transacao.getDataTransacao(),
                transacao.getDescricao()
        );
        return req;
    }

    private static Categoria toCategoria(CategoriaRequest req){
        Categoria categoria = new Categoria();
        categoria.setNome(req.nome());
        return categoria;
    }

    public static CategoriaResponse toCategoriaResponse(Categoria categoria){
        CategoriaResponse req = new CategoriaResponse(categoria.getId(), categoria.getNome());
        return req;
    }


}
