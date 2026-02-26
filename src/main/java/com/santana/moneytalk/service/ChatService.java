package com.santana.moneytalk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.santana.moneytalk.domain.dto.request.TransacaoRequest;
import com.santana.moneytalk.domain.dto.response.TransacaoResponse;
import com.santana.moneytalk.domain.model.Categoria;
import com.santana.moneytalk.domain.model.Transacao;
import com.santana.moneytalk.mapper.JsonMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChatService {

    private final TransacaoService transacaoService;
    private final ChatClient client;
    private final CategoriaService categoriaService;
    private final JsonMapper jsonMapper;

    public ChatService(ChatClient.Builder builder, CategoriaService categoriaService, TransacaoService transacaoService, JsonMapper jsonMapper) {
        this.client = builder
                .defaultSystem("Você é um consultor financeiro/chatCriarTransacao que vai ajudar o usuário a organizar as suas finanças, ira receber informações do usuário e vai auxilia-lo, responda sempre com o idioma que receber a mensagem")
                .build();
        this.categoriaService = categoriaService;
        this.transacaoService = transacaoService;
        this.jsonMapper = jsonMapper;
    }

    public TransacaoResponse chatCriarTransacao(String message){
        List<Categoria> categorias = categoriaService.findAll();

        var transacaoRequest = client
                .prompt(prompt(message, jsonMapper.toJson(categorias)))
                .call()
                .entity(TransacaoRequest.class);
        return transacaoService.criarTransacao(transacaoRequest);
    }

    public String analiseIa(List<Transacao> transacoes){
        return client
                .prompt("Responda com poucas palavras de texto, no máximo 950 caracteres, não use emojis, apenas texto, se possível não pule nem linha, o texto sera salvo no banco de dados como uma string normal, tente organizar")
                .user("Faça a análise das seguintes transações: " + jsonMapper.toJson(transacoes))
                .call()
                .content();
    }



    private String prompt(String message, String categoria){
        return """
                Você é um especialista em classificação financeira.
                Sua única tarefa é criar uma instância do objeto JSON 'Transacao' a partir da mensagem enviada pelo usuário.

                Pode ser mais genérico nas categorias, como: TRANSPORTE, ALIMENTACAO, etc.
                Responda na lingua que a mensagem vier, se falar portugues fale em portugues
                
                ### Contexto de Tempo
                                - **Data de Referência (HOJE)**: %s
                                - **Regra de Data**: Use a Data de Referência para calcular datas relativas. Por exemplo: se HOJE é 2026-01-05, e o usuário diz 'ontem', use 2026-01-04. Se o usuário não especificar a data, use a Data de Referência.
                ### Informações
                - **Mensagem do Usuário**: "%s"
                - **Categorias Existentes**: Use apenas uma das seguintes categorias se for um bom fit: [%s].
                - **Regra de Criação de Categoria**: Se nenhuma das categorias existentes for adequada, você DEVE criar uma nova categoria relevante para a transação.
                - **Estrutura do Objeto Transacao**: Double valor, Objeto Categoria (com nome), TipoTransacao enum(ENTRADA, SAIDA), dataTransacao (Data atual se não for especificada e não pode ser no futuro), e descricao (personalizada pela IA).
                
                Retorne APENAS o JSON.
                """.formatted(LocalDate.now(), message, categoria);
    }
}
