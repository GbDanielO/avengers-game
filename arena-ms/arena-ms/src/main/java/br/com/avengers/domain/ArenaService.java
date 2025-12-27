package br.com.avengers.domain;

import br.com.avengers.domain.model.Batalha;
import br.com.avengers.ports.in.MessagePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArenaService implements MessagePort {

    private final ArenaEngine arenaEngine;

    @Autowired
    public ArenaService(ArenaEngine arenaEngine) {
        this.arenaEngine = arenaEngine;
    }

    @Override
    public void processaBatalha(Batalha batalha) {
        Batalha batalhaFinalizada = arenaEngine.execute(batalha);
        System.out.println(batalhaFinalizada);
    }
}
