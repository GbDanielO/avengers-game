package br.com.avengers.domain;

import br.com.avengers.domain.model.Batalha;
import br.com.avengers.ports.in.MessagePort;
import br.com.avengers.ports.out.ArenaRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArenaService implements MessagePort {

    private final ArenaEngine arenaEngine;
    private final ArenaRepositoryPort arenaRepositoryPort;

    @Autowired
    public ArenaService(ArenaEngine arenaEngine, ArenaRepositoryPort arenaRepositoryPort) {
        this.arenaEngine = arenaEngine;
        this.arenaRepositoryPort = arenaRepositoryPort;
    }

    @Override
    public void processaBatalha(Batalha batalha) {
        Batalha batalhaFinalizada = arenaEngine.execute(batalha);
        arenaRepositoryPort.salvar(batalhaFinalizada);
    }
}
