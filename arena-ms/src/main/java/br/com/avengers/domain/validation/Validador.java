package br.com.avengers.domain.validation;

import br.com.avengers.domain.model.Batalha;

import java.util.List;

public interface Validador {

    public void valida(Batalha batalha, List<String> erros);

}
