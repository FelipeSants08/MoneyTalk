package com.santana.moneytalk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.santana.moneytalk.domain.dto.request.TransacaoRequest;
import jakarta.transaction.Transactional;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ChatService {

    private ChatClient client;
    private CategoriaService categoriaService;
    private TransacaoAi transacaoAi;

    public ChatService(ChatClient.Builder builder, CategoriaService categoriaService, TransacaoAi transacaoAi) {
        this.client = builder
                .defaultSystem("Você é um consultor financeiro/chat que vai ajudar o usuário a organizar as suas finanças, ira receber informações do usuário e vai auxilia-lo, responda sempre com o idioma que receber a mensagem")
                .build();
        this.categoriaService = categoriaService;
        this.transacaoAi = transacaoAi;
    }

    public TransacaoRequest chat(String message){

        String dataAtual = LocalDate.now().toString();

        String categorias;
        try {
            ObjectMapper mapper = new ObjectMapper();
            categorias = mapper.writeValueAsString(categoriaService.findAll());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao serializar JSON");
        }

        String prompt = """
                Você é um especialista em classificação financeira.
                Sua única tarefa é criar uma instância do objeto JSON 'Transacao' a partir da mensagem enviada pelo usuário.

                Pode ser mais genérico nas categorias, como: Transporte, Alimentação, etc.
                Responda na lingua que a mensagem vier, se falar portugues fale em portugues
                
                ### Contexto de Tempo
                                - **Data de Referência (HOJE)**: %s
                                - **Regra de Data**: Use a Data de Referência para calcular datas relativas. Por exemplo: se HOJE é 2026-01-05, e o usuário diz 'ontem', use 2026-01-04. Se o usuário não especificar a data, use a Data de Referência.
                ### Informações
                - **Mensagem do Usuário**: "%s"
                - **Categorias Existentes**: Use apenas uma das seguintes categorias se for um bom fit: [%s].
                - **Regra de Criação de Categoria**: Se nenhuma das categorias existentes for adequada, você DEVE criar uma nova categoria relevante para a transação.
                - **Estrutura do Objeto Transacao**: Double valor, Objeto Categoria (com nome), TipoTransacao enum(ENTRADA, SAIDA), dataTransacao (Data atual se não for especificada), e descricao (personalizada pela IA).
                
                Retorne APENAS o JSON.
                """.formatted(dataAtual, message, categorias);


        var transacaoRequest = client
                .prompt(prompt)
                .call()
                .entity(TransacaoRequest.class);
        transacaoAi.processarESalvar(transacaoRequest);
        return transacaoRequest;
    }

    public String analiseIa(){
        String transacoes;
        try {
            ObjectMapper mapper = new ObjectMapper();
            transacoes = mapper.writeValueAsString(transacaoAi.pegarTransacoesAnalise());
        }catch (JsonProcessingException e){
            throw new RuntimeException("Erro ao serializar JSON");
        }
        return client
                .prompt()
                .user("Faça a análise das seguintes transações: " + transacoes)
                .call()
                .content();
    }

}
