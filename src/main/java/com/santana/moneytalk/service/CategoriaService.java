package com.santana.moneytalk.service;

import com.santana.moneytalk.domain.dto.request.CategoriaRequest;
import com.santana.moneytalk.domain.model.Categoria;
import com.santana.moneytalk.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;

    public Categoria save(CategoriaRequest request){
        return repository.save(dto(request));
    }
    public List<Categoria> findAll(){
        return repository.findAll();
    }
    public Categoria dto(CategoriaRequest request){
        Categoria categoria = new Categoria();
        categoria.setNome(request.nome());
        return categoria;
    }

    public Categoria buscarOuCriarCategoria(CategoriaRequest request){
        return pegarPorNome(request.nome())
                .orElseGet(() -> save(request));
    }

    public Optional<Categoria> pegarPorNome(String nome){
        return repository.pegarPorNome(nome);
    }

}
