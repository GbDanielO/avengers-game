package br.com.avengers.ports.out;

import br.com.avengers.adapters.out.persistence.entity.Avenger;

import java.util.List;
import java.util.Optional;

public interface AvengerRepositoryPort {

    List<Avenger> findAll();

    Optional<Avenger> findById(Long id);

    Avenger create(Avenger avenger);

    Avenger update(Avenger avenger);

    void delete(Long id);

    Avenger findByApelido(String apelido);

}
