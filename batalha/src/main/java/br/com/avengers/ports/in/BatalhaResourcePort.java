package br.com.avengers.ports.in;

import br.com.avengers.adapters.dto.BatalhaResultadoDTO;
import br.com.avengers.domain.model.DoisJogadores;
import br.com.avengers.domain.model.JogoResponse;
import br.com.avengers.domain.model.UmJogador;

public interface BatalhaResourcePort {

    BatalhaResultadoDTO findById(String protocoloId);

    JogoResponse criarJogadaUmJogador(UmJogador umJogador);

    JogoResponse criarJogadaDoisJogadores(DoisJogadores doisJogadores);
}
