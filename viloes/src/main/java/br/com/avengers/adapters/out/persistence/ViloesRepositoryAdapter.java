package br.com.avengers.adapters.out.persistence;

import br.com.avengers.adapters.out.persistence.entity.Vilao;
import br.com.avengers.ports.out.ViloesRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ViloesRepositoryAdapter implements ViloesRepositoryPort {

    private final VilaoRepository vilaoRepository;

    @Autowired
    public ViloesRepositoryAdapter(VilaoRepository vilaoRepository) {
        this.vilaoRepository = vilaoRepository;
    }


    @Override
    public List<Vilao> findAll() {
        return vilaoRepository.findAll();
    }

    @Override
    public Optional<Vilao> findById(Long id) {
        return vilaoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        vilaoRepository.deleteById(id);
    }

    @Override
    public Optional<Vilao> findByApelido(String apelido) {
        return vilaoRepository.findByApelidoIgnoreCase(apelido);
    }

    @Override
    public Vilao update(Vilao vilao) {
        return vilaoRepository.save(vilao);
    }

    @Override
    public Vilao create(Vilao vilao) {
        return vilaoRepository.save(vilao);
    }
}
