package com.santana.moneytalk.controller;

import com.santana.moneytalk.domain.dto.request.TransacaoRequest;
import com.santana.moneytalk.domain.model.Transacao;
import com.santana.moneytalk.service.ChatService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<TransacaoRequest> sendMessage(@RequestBody String message){
       return ResponseEntity.ok(service.chat(message));
    }

    @GetMapping("analise")
    public ResponseEntity<String> pegarAnalise(){
        return ResponseEntity.ok(service.analiseIa());
    }
}
