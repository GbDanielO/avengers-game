package br.com.avengers.adapters.out.persistence;

import br.com.avengers.application.DLQMessageDTO;
import br.com.avengers.ports.out.DLQRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DLQRepositoryAdapter implements DLQRepositoryPort {

    private final DLQRepository dlqRepository;

    @Autowired
    public DLQRepositoryAdapter(DLQRepository dlqRepository) {
        this.dlqRepository = dlqRepository;
    }

    @Override
    public Optional<DLQMessageDTO> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<DLQMessageDTO> findByTraceId(String traceId) {
        return dlqRepository.findByTraceId(traceId);
    }

    @Override
    public void save(DLQMessageDTO dlqMessageDTO) {
        dlqRepository.save(dlqMessageDTO);
    }
}
