package br.com.avengers.adapters.out.persistence;

import br.com.avengers.adapters.dto.BatalhaResultadoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatalhaRepository extends MongoRepository<BatalhaResultadoDTO, String> {

    public BatalhaResultadoDTO findByProtocolo(String protocolo);

}
