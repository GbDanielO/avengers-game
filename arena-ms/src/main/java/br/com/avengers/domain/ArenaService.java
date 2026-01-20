package br.com.avengers.domain;

import br.com.avengers.domain.model.Batalha;
import br.com.avengers.domain.validation.ValidacaoChain;
import br.com.avengers.ports.in.MessagePort;
import br.com.avengers.ports.out.ArenaRepositoryPort;
import br.com.avengers.shared.NegocioException;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ArenaService implements MessagePort {

    private final ValidacaoChain validacaoChain;
    private final ArenaEngine arenaEngine;
    private final ArenaRepositoryPort arenaRepositoryPort;

    @Autowired
    public ArenaService(ValidacaoChain validacaoChain, ArenaEngine arenaEngine, ArenaRepositoryPort arenaRepositoryPort) {
        this.validacaoChain = validacaoChain;
        this.arenaEngine = arenaEngine;
        this.arenaRepositoryPort = arenaRepositoryPort;
    }

    @Override
    public void processaBatalha(Batalha batalha, String traceId) {
        ThreadContext.put("traceId", traceId);

        try {
            log.info("Processando batalha na Arena");
            this.validacaoChain.valida(batalha);
            Batalha batalhaFinalizada = this.arenaEngine.execute(batalha);
            this.arenaRepositoryPort.salvar(batalhaFinalizada);
            log.info("Batalha finalizada na Arena");
        } catch (NegocioException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            //demais erros logo e relanço para não atrapalhar retry
            log.error(e.getMessage(), e);
            throw e;
        }finally {
            ThreadContext.clearAll();
        }
    }
}
