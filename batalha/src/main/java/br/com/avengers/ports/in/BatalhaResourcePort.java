package br.com.avengers.ports.in;

import br.com.avengers.domain.model.DoisJogadores;
import br.com.avengers.domain.model.JogoResponse;
import br.com.avengers.domain.model.Batalha;
import br.com.avengers.domain.model.UmJogador;

public interface BatalhaResourcePort {

    Batalha findById(String protocoloId);

    JogoResponse criarJogadaUmJogador(UmJogador umJogador);

    JogoResponse criarJogadaDoisJogadores(DoisJogadores doisJogadores);
}
