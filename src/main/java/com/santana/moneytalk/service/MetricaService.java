package com.santana.moneytalk.service;

import com.santana.moneytalk.domain.dto.request.CriarMetricaPorData;
import com.santana.moneytalk.domain.dto.response.MetricaResponse;
import com.santana.moneytalk.domain.model.Metrica;
import com.santana.moneytalk.domain.model.Transacao;
import com.santana.moneytalk.repository.MetricaRepository;
import com.santana.moneytalk.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetricaService {

    private final TransacaoRepository transacaoRepository;
    private final MetricaRepository metricaRepository;
    private final ChatService chatService;


    public MetricaService(TransacaoRepository transacaoRepository, MetricaRepository metricaRepository, ChatService chatService) {
        this.transacaoRepository = transacaoRepository;
        this.metricaRepository = metricaRepository;
        this.chatService = chatService;
    }

    public MetricaResponse gerarMetrica(CriarMetricaPorData dto){
        verificarSeComecoEDepoisDoPassado(dto);
        List<Transacao> transacoes = transacaoRepository.transacoesPorDataComecoEFim(dto.inicio(), dto.fim());

        String categoriaMaisCara = transacaoRepository.categoriaMaisCaraNoPeriodo(dto.inicio(), dto.fim());

        String analiseIa = chatService.analiseIa(transacoes);


        Metrica metrica = new Metrica(transacoes, analiseIa, categoriaMaisCara);

        metricaRepository.save(metrica);
        return new MetricaResponse(metrica);
    }

    private void verificarSeComecoEDepoisDoPassado(CriarMetricaPorData dto){
        if (dto.inicio().isAfter(dto.fim())){
            throw new RuntimeException("A data de início não pode ser posterior à data final.");
        }
    }

}
