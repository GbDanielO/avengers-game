package br.com.avengers.adapters.out.persistence;

import br.com.avengers.application.DLQMessageDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DLQRepository extends MongoRepository<DLQMessageDTO, String> {

    Optional<DLQMessageDTO> findByTraceId(String traceId);
}
