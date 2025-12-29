package br.com.avengers.ports.out;

import br.com.avengers.adapters.dto.BatalhaResultadoDTO;

public interface BatalhaRepositoryPort {
    BatalhaResultadoDTO buscarPorProtocolo(String protocolo);
}
