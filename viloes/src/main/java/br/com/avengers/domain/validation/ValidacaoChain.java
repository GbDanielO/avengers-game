package br.com.avengers.domain.validation;

import br.com.avengers.adapters.out.persistence.entity.Vilao;
import br.com.avengers.shared.ResponseError;
import br.com.avengers.shared.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValidacaoChain {

    /**
     * Spring vai injetar todos os validadores
     */
    private final List<Validador> validadores;

    @Autowired
    public ValidacaoChain(List<Validador> validadores) {
        this.validadores = validadores;
    }

    public void valida(Vilao vilao){
        List<ResponseError> erros = new ArrayList<>();

        validadores.forEach(validador -> validador.valida(vilao, erros));

        if(!erros.isEmpty()){
            throw new ValidacaoException(erros);
        }
    }
}
