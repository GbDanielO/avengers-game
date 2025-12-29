package br.com.avengers.domain;

import br.com.avengers.adapters.out.persistence.entity.Avenger;
import br.com.avengers.ports.in.AvengerResourcePort;
import br.com.avengers.ports.out.AvengerRepositoryPort;
import br.com.avengers.shared.NegocioException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AvengerService implements AvengerResourcePort {

    private final AvengerRepositoryPort avengerRepositoryPort;

    @Autowired
    public AvengerService(AvengerRepositoryPort avengerRepositoryPort) {
        this.avengerRepositoryPort = avengerRepositoryPort;
    }

    @Override
    public List<Avenger> findAll() {
        return avengerRepositoryPort.findAll();
    }

    @Override
    public Avenger findById(Long id) {
        return avengerRepositoryPort.findById(id)
                .orElseThrow(() -> new NegocioException("Vingador não encontrado pelo ID: " + id, HttpStatus.BAD_REQUEST));
    }

    @Override
    public Avenger findByApelido(String apelido) {
        return avengerRepositoryPort.findByApelido(apelido);
    }

    @Transactional
    @Override
    public Avenger update(Avenger avenger) {
        return avengerRepositoryPort.update(avenger);
    }

    @Transactional
    @Override
    public Avenger create(Avenger avenger) {
        return avengerRepositoryPort.create(avenger);
    }

    @Transactional
    @Override
    public List<Avenger> create(List<Avenger> avengers) {
        List<Avenger> avengerSalvos = new ArrayList<>();
        for (Avenger avenger : avengers) {
            avenger = avengerRepositoryPort.create(avenger);
            avengerSalvos.add(avenger);
        }
        return avengerSalvos;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        avengerRepositoryPort.delete(id);
    }
}
