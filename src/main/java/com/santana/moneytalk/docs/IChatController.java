package com.santana.moneytalk.docs;

import com.santana.moneytalk.domain.dto.response.TransacaoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "IA", description = "Operações da IA na API")
public interface IChatController {

    @Operation(description = "Criar uma transação com linguagem natural", summary = "Transação com IA")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação criada"),
            @ApiResponse(responseCode = "400", description = "Falta de dados")
    })
    TransacaoResponse sendMessage(String message);

    String pegarAnalise();
}
