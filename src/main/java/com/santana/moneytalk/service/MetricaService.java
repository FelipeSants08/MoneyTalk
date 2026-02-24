package com.santana.moneytalk.service;

import com.santana.moneytalk.domain.dto.request.CriarMetricaPorData;
import com.santana.moneytalk.domain.dto.response.MetricaResponse;
import com.santana.moneytalk.domain.model.Categoria;
import com.santana.moneytalk.domain.model.Metrica;
import com.santana.moneytalk.domain.model.TipoTransacao;
import com.santana.moneytalk.domain.model.Transacao;
import com.santana.moneytalk.repository.MetricaRepository;
import com.santana.moneytalk.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        Double totalDeSaidaNoPeriodo = transacaoRepository.totalDeSaidaNoPeriodo(dto.inicio(), dto.fim()).orElse(0.0);
        Double totalDeEntradaNoPeriodo = transacaoRepository.totalDeEntradasNoPeriodo(dto.inicio(), dto.fim()).orElse(0.0);
        Double saldo = (totalDeEntradaNoPeriodo - totalDeSaidaNoPeriodo);
        String categoriaMaisCara = transacaoRepository.categoriaMaisCaraNoPeriodo(dto.inicio(), dto.fim());

        String analise = chatService.analiseIa(transacoes);

        Integer quantidadeTransacoes = transacoes.size();
        Double media = transacoes.stream()
                .filter(t -> t.getTipo().equals(TipoTransacao.SAIDA))
                .mapToDouble(Transacao::getValor)
                .average()
                .orElse(0.0);

        Metrica metrica = new Metrica(quantidadeTransacoes, saldo, media, totalDeEntradaNoPeriodo, totalDeSaidaNoPeriodo, analise, categoriaMaisCara);

        metricaRepository.save(metrica);
        return new MetricaResponse(metrica);
    }

    private void verificarSeComecoEDepoisDoPassado(CriarMetricaPorData dto){
        if (dto.inicio().isAfter(dto.fim())){
            throw new RuntimeException("Inicio é depois da data final");
        }
    }

}
