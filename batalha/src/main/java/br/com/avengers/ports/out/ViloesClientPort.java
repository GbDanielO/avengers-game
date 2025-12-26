package br.com.avengers.ports.out;

import br.com.avengers.domain.model.Personagem;

public interface ViloesClientPort {
    Personagem buscarPorApelido(String apelido);
}
