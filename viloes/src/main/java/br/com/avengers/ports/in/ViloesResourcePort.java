package br.com.avengers.ports.in;

import br.com.avengers.adapters.out.persistence.entity.Vilao;

import java.util.List;

public interface ViloesResourcePort {

    List<Vilao> findAll();

    Vilao findById(Long id);

    Vilao create(Vilao vilao);

    Vilao update(Vilao vilao);

    Vilao findByApelido(String apelido);

    void delete(Long id);

    List<Vilao> create(List<Vilao> list);
}
