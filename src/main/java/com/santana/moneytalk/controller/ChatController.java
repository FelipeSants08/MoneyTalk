package com.santana.moneytalk.controller;

import com.santana.moneytalk.docs.IChatController;
import com.santana.moneytalk.domain.dto.request.CriarMetricaPorData;
import com.santana.moneytalk.domain.dto.response.MetricaResponse;
import com.santana.moneytalk.domain.dto.response.TransacaoResponse;
import com.santana.moneytalk.service.ChatService;
import com.santana.moneytalk.service.MetricaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("chat")
public class ChatController implements IChatController {

    private final ChatService service;
    private final MetricaService metricaService;

    public ChatController(ChatService service, MetricaService metricaService) {
        this.service = service;
        this.metricaService = metricaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransacaoResponse sendMessage(@RequestBody String message){
       return service.chatCriarTransacao(message);
    }

    @PostMapping("analise")
    @ResponseStatus(HttpStatus.OK)
    public MetricaResponse pegarAnalise(@RequestBody CriarMetricaPorData dto){
        return metricaService.gerarMetrica(dto);
    }
}
