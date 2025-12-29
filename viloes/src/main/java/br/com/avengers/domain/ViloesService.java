package br.com.avengers.domain;

import br.com.avengers.adapters.out.persistence.entity.Vilao;
import br.com.avengers.ports.in.ViloesResourcePort;
import br.com.avengers.ports.out.ViloesRepositoryPort;
import br.com.avengers.shared.NegocioException;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ViloesService implements ViloesResourcePort {

    private final ViloesRepositoryPort viloesRepositoryPort;

    @Autowired
    public ViloesService(ViloesRepositoryPort viloesRepositoryPort) {
        this.viloesRepositoryPort = viloesRepositoryPort;
    }

    @Override
    public List<Vilao> findAll() {
        return viloesRepositoryPort.findAll();
    }

    @Override
    public Vilao findById(Long id) {
        return viloesRepositoryPort.findById(id)
                .orElseThrow(() -> new NegocioException("Vilão não encontrado pelo ID: " + id, HttpStatus.BAD_REQUEST));
    }

    @Override
    public Vilao findByApelido(String apelido) {
        return viloesRepositoryPort.findByApelido(apelido)
                .orElseThrow(() -> new NegocioException("Vilão não encontrado apelido: " + apelido, HttpStatus.BAD_REQUEST));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        viloesRepositoryPort.delete(id);
    }

    @Transactional
    @Override
    public List<Vilao> create(List<Vilao> viloes) {
        List<Vilao> viloesSalvos = new ArrayList<>();
        for(Vilao v: viloes){
            v = viloesRepositoryPort.create(v);
            viloesSalvos.add(v);
        }
        return viloesSalvos;
    }

    @Transactional
    @Override
    public Vilao update(Vilao vilao) {
        return viloesRepositoryPort.update(vilao);
    }

    @Transactional
    @Override
    public Vilao create(Vilao vilao) {
        return viloesRepositoryPort.create(vilao);
    }
}
