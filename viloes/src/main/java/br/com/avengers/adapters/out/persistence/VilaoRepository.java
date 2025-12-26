package br.com.avengers.adapters.out.persistence;

import br.com.avengers.adapters.out.persistence.entity.Vilao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VilaoRepository extends JpaRepository<Vilao, Long> {

    public Optional<Vilao> findByApelido(String apelido);
}
