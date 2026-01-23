package com.santana.moneytalk.controller;

import com.santana.moneytalk.domain.dto.request.TransacaoRequest;
import com.santana.moneytalk.domain.dto.response.TransacaoResponse;
import com.santana.moneytalk.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("chat")
@Tag(name = "IA", description = "Operações da IA na API")
public class ChatController {

    private final ChatService service;

    public ChatController(ChatService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Criar uma transação com linguagem natural", summary = "Transação com IA")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação criada"),
            @ApiResponse(responseCode = "400", description = "Falta de dados")
    })
    public TransacaoResponse sendMessage(@RequestBody String message){
       return service.chatCriarTransacao(message);
    }

    @GetMapping("analise")
    public ResponseEntity<String> pegarAnalise(){
        return ResponseEntity.ok(service.analiseIa());
    }
}
