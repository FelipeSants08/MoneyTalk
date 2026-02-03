package com.santana.moneytalk.controller;

import com.santana.moneytalk.docs.IChatController;
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
public class ChatController implements IChatController {

    private final ChatService service;

    public ChatController(ChatService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransacaoResponse sendMessage(@RequestBody String message){
       return service.chatCriarTransacao(message);
    }

    @GetMapping("analise")
    @ResponseStatus(HttpStatus.OK)
    public String pegarAnalise(){
        return service.analiseIa();
    }
}
