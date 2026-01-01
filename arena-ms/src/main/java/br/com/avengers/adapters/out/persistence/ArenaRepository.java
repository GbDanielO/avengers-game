package br.com.avengers.adapters.out.persistence;

import br.com.avengers.adapters.dto.BatalhaResultadoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArenaRepository extends MongoRepository<BatalhaResultadoDTO, String> {

}
