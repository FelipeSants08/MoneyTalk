package com.santana.moneytalk.controller;

import com.santana.moneytalk.domain.dto.request.TransacaoRequest;
import com.santana.moneytalk.domain.dto.response.TransacaoResponse;
import com.santana.moneytalk.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("chat")
public class ChatController {

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
    public ResponseEntity<String> pegarAnalise(){
        return ResponseEntity.ok(service.analiseIa());
    }
}
