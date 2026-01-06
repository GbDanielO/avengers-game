package br.com.avengers.domain;

import br.com.avengers.domain.model.Batalha;
import br.com.avengers.ports.in.MessagePort;
import br.com.avengers.ports.out.ArenaRepositoryPort;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
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
    public void processaBatalha(Batalha batalha, String traceId) {
        try (var context = CloseableThreadContext.put("traceId", traceId)){
            log.info("Processando batalha na Arena");
            Batalha batalhaFinalizada = arenaEngine.execute(batalha);
            arenaRepositoryPort.salvar(batalhaFinalizada);
            log.info("Batalha finalizada na Arena");
        }
    }
}
