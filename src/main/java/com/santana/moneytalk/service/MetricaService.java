package com.santana.moneytalk.service;

import com.santana.moneytalk.domain.dto.request.CriarMetricaPorData;
import com.santana.moneytalk.domain.dto.response.MetricaResponse;
import com.santana.moneytalk.domain.model.Metrica;
import com.santana.moneytalk.domain.model.TipoTransacao;
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
        metrica.setTotalEntradas(calcularTotal(transacoes, TipoTransacao.ENTRADA));
        metrica.setTotalSaida(calcularTotal(transacoes, TipoTransacao.SAIDA));
        metrica.setMediaSaida(mediaSaida(transacoes));

        metricaRepository.save(metrica);
        return new MetricaResponse(metrica);
    }

    private void verificarSeComecoEDepoisDoPassado(CriarMetricaPorData dto){
        if (dto.inicio().isAfter(dto.fim())){
            throw new RuntimeException("A data de início não pode ser posterior à data final.");
        }
    }



    private Double calcularTotal(List<Transacao> transacoes, TipoTransacao tipoTransacao) {
            return transacoes.stream()
                    .filter(t -> t.getTipo() == tipoTransacao)
                    .mapToDouble(Transacao::getValor)
                    .sum();
    }
    private Double mediaSaida(List<Transacao> transacoes) {
        return transacoes.stream()
                .filter(t-> t.getTipo() == TipoTransacao.SAIDA)
                .mapToDouble(Transacao::getValor)
                .average().orElse(0.0);
    }

}



