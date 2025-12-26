package br.com.avengers.ports.out;

import br.com.avengers.domain.model.DoisJogadores;

import java.util.Optional;

public interface CacheDBPort {

    // Quando o jogo é jogado por 2 jogadores, cada um manda um personagem
    void salvar(String idJogo, DoisJogadores jogo);

    Optional<DoisJogadores> buscarPorIdJogo(String idJogo);

    void remover(String idJogo);
}
