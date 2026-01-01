package br.com.avengers.domain.validations;

import br.com.avengers.adapters.out.persistence.entity.Avenger;
import br.com.avengers.shared.ResponseError;

import java.util.List;

/**
 * Aqui trago a implementação base do Chain of Responsability
 * classica, sem uso do Spring, Injeção de Dependencia ou Inversão de controle.
 */
public interface Validador {
    public void valida(Avenger avenger, List<ResponseError> erros);
}
