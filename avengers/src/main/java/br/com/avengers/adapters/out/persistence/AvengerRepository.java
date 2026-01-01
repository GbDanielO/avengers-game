package br.com.avengers.adapters.out.persistence;

import br.com.avengers.adapters.out.persistence.entity.Avenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvengerRepository extends JpaRepository<Avenger, Long> {

    Avenger findByApelidoIgnoreCase(String apelido);
}
