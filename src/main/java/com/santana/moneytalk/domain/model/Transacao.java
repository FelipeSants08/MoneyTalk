package com.santana.moneytalk.domain.model;

import com.santana.moneytalk.domain.dto.request.TransacaoRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valor;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    private LocalDate dataTransacao;

    private String descricao;

    public Transacao(TransacaoRequest req, Categoria categoria){
        Transacao transacao = new Transacao();
        transacao.setCategoria(categoria);
        transacao.setDataTransacao(req.dataTransacao());
        transacao.setValor(req.valor());
        transacao.setDescricao(req.descricao());
        transacao.setTipo(req.tipo());
    }


}
