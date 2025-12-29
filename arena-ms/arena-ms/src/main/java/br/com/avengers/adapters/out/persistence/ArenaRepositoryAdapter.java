package br.com.avengers.adapters.out.persistence;

import br.com.avengers.adapters.dto.BatalhaResultadoDTO;
import br.com.avengers.domain.model.Batalha;
import br.com.avengers.ports.out.ArenaRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArenaRepositoryAdapter implements ArenaRepositoryPort {

    private final ArenaRepository arenaRepository;

    @Autowired
    public ArenaRepositoryAdapter(ArenaRepository arenaRepository) {
        this.arenaRepository = arenaRepository;
    }

    @Override
    public void salvar(Batalha batalha){
        arenaRepository.save(BatalhaResultadoDTO.getInstanceFrom(batalha));
    }
}
