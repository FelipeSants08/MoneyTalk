package com.santana.moneytalk.docs;

import com.santana.moneytalk.domain.dto.request.AlteraTransacaoRequest;
import com.santana.moneytalk.domain.dto.request.CategoriaRequest;
import com.santana.moneytalk.domain.dto.request.TransacaoRequest;
import com.santana.moneytalk.domain.dto.response.TransacaoResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name= "Transação", description = "API de gerenciamento de transações")
public interface ITransacaoController {

    List<TransacaoResponse> transacoes();
    TransacaoResponse criar(TransacaoRequest request);
    void atualizar(Long id, AlteraTransacaoRequest request);
    void atualizarCategoria(Long id, CategoriaRequest request);
    void deletar(Long id);

}
