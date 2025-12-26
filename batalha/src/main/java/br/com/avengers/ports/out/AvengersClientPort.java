package br.com.avengers.ports.out;

import br.com.avengers.domain.model.Personagem;

public interface AvengersClientPort {


    Personagem buscarPorApelido(String apelido);
}
