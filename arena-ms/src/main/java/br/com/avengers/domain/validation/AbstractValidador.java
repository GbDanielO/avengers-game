package br.com.avengers.domain.validation;

import br.com.avengers.domain.model.Batalha;

import java.util.List;

public abstract class AbstractValidador implements Validador{

    protected Validador proximo;

    public AbstractValidador setProximo(Validador proximo) {
        this.proximo = proximo;
        return (AbstractValidador) proximo;
    }

    protected void chamaProximo(Batalha batalha, List<String> erros){
        if(proximo != null){
            proximo.valida(batalha, erros);
        }
    }
}
