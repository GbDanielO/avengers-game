package br.com.avengers.domain.validations;

import br.com.avengers.adapters.out.persistence.entity.Avenger;
import br.com.avengers.shared.ResponseError;

import java.util.List;

public abstract class AbstractValidador implements Validador{

    /**
     * Próximo nó da cadeia que vai validar
     */
    protected Validador proximo;

    public AbstractValidador setProximo(Validador proximo){
        this.proximo = proximo;
        return (AbstractValidador) proximo;
    }

    protected void chamaProximo(Avenger avenger, List<ResponseError> erros){
        if(proximo != null){
            proximo.valida(avenger, erros);
        }
    }


}
