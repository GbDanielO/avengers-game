package br.com.avengers.ports.out;

import br.com.avengers.adapters.out.persistence.entity.Vilao;

import java.util.List;
import java.util.Optional;

public interface ViloesRepositoryPort {

    List<Vilao> findAll();

    Optional<Vilao> findById(Long id);

    Vilao create(Vilao vilao);

    Vilao update(Vilao vilao);

    void delete(Long id);

    Optional<Vilao> findByApelido(String apelido);
}
