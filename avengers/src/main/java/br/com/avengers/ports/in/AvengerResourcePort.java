package br.com.avengers.ports.in;

import br.com.avengers.adapters.out.persistence.entity.Avenger;

import java.util.List;

public interface AvengerResourcePort {

    List<Avenger> findAll();

    Avenger findById(Long id);

    Avenger create(Avenger avenger);

    List<Avenger> create(List<Avenger> avengers);

    Avenger update(Avenger avenger);

    void delete(Long id);

    Avenger findByApelido(String apelido);


}
