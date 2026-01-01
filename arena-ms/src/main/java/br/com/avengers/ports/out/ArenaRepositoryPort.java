package br.com.avengers.ports.out;

import br.com.avengers.domain.model.Batalha;

public interface ArenaRepositoryPort {
    void salvar(Batalha batalha);
}
