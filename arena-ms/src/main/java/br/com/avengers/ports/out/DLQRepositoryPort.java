package br.com.avengers.ports.out;

import br.com.avengers.application.DLQMessageDTO;

import java.util.Optional;

public interface DLQRepositoryPort {

    Optional<DLQMessageDTO> findById(String id);

    Optional<DLQMessageDTO> findByTraceId(String traceId);

    void save(DLQMessageDTO entity);
}
