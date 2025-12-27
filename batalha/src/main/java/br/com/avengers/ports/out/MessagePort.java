package br.com.avengers.ports.out;

import br.com.avengers.domain.model.Batalha;

public interface MessagePort {
    public void enviarMensagem(String Topico, Batalha batalha);
}
