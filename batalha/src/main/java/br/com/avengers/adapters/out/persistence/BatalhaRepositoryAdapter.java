package br.com.avengers.adapters.out.persistence;

import br.com.avengers.adapters.dto.BatalhaResultadoDTO;
import br.com.avengers.ports.out.BatalhaRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BatalhaRepositoryAdapter implements BatalhaRepositoryPort {

    private final BatalhaRepository batalhaRepository;

    @Autowired
    public BatalhaRepositoryAdapter(BatalhaRepository batalhaRepository) {
        this.batalhaRepository = batalhaRepository;
    }

    @Override
    public BatalhaResultadoDTO buscarPorProtocolo(String protocolo){
        return batalhaRepository.findByProtocolo(protocolo);
    }
}
