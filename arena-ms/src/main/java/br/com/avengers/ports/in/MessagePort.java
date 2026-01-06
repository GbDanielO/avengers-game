package br.com.avengers.ports.in;

import br.com.avengers.domain.model.Batalha;

public interface MessagePort {
    public void processaBatalha(Batalha batalha, String traceId);
}
