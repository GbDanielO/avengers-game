package br.com.avengers.adapters.out.persistence;

import br.com.avengers.adapters.out.persistence.entity.Avenger;
import br.com.avengers.ports.out.AvengerRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AvengerRepositoryAdapter implements AvengerRepositoryPort {

    private final AvengerRepository avengerRepository;

    @Autowired
    public AvengerRepositoryAdapter(AvengerRepository avengerRepository) {
        this.avengerRepository = avengerRepository;
    }

    @Override
    public List<Avenger> findAll() {
        return avengerRepository.findAll();
    }

    @Override
    public Optional<Avenger> findById(Long id) {
        return avengerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        avengerRepository.deleteById(id);
    }

    @Override
    public Avenger findByApelido(String apelido) {
        return avengerRepository.findByApelidoIgnoreCase(apelido);
    }

    @Override
    public Avenger update(Avenger avenger) {
        return avengerRepository.save(avenger);
    }

    @Override
    public Avenger create(Avenger avenger) {
        return avengerRepository.save(avenger);
    }
}
