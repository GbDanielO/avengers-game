package br.com.avengers.domain;

import br.com.avengers.adapters.out.persistence.entity.Avenger;
import br.com.avengers.domain.validations.ValidacaoChain;
import br.com.avengers.ports.in.AvengerResourcePort;
import br.com.avengers.ports.out.AvengerRepositoryPort;
import br.com.avengers.shared.NegocioException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AvengerService implements AvengerResourcePort {

    private final AvengerRepositoryPort avengerRepositoryPort;
    private final ValidacaoChain validacaoChain;

    @Autowired
    public AvengerService(AvengerRepositoryPort avengerRepositoryPort, ValidacaoChain validacaoChain) {
        this.avengerRepositoryPort = avengerRepositoryPort;
        this.validacaoChain = validacaoChain;
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
        return Optional.ofNullable(avengerRepositoryPort.findByApelido(apelido))
                .orElseThrow(() -> new NegocioException("Vingador não encontrado pelo apelido: " + apelido, HttpStatus.BAD_REQUEST));
    }

    @Transactional
    @Override
    public Avenger update(Avenger avenger) {
        validacaoChain.valida(avenger);
        return avengerRepositoryPort.update(avenger);
    }

    @Transactional
    @Override
    public Avenger create(Avenger avenger) {
        validacaoChain.valida(avenger);
        return avengerRepositoryPort.create(avenger);
    }

    @Transactional
    @Override
    public List<Avenger> create(List<Avenger> avengers) {
        List<Avenger> avengerSalvos = new ArrayList<>();
        for (Avenger avenger : avengers) {
            /*
                Aqui entra uma fase importante de conhecer transação e saber aplicar
                de acordo com o contexto e a necessidade do negócio. Se quiser salvar
                apenas a lista completa, deixo a validação sem try/catch e se algum registro
                der erro, não salva nada. Se quiser salvar os itens que deram certo, posso
                envolver a validação num try/catch e salvar os que passaram e retornar apenas
                eles, deixando os que deram erro pra lá ou fazendo algum tratamento.
             */
            validacaoChain.valida(avenger);
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
