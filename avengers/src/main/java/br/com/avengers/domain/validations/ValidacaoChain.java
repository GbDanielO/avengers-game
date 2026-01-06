package br.com.avengers.domain.validations;

import br.com.avengers.adapters.out.persistence.entity.Avenger;
import br.com.avengers.shared.ResponseError;
import br.com.avengers.shared.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por montar a cadeia de validadores.
 * Caso haja uma ordem aqui o dev coloca eles em ordem!
 * Usei Spring para a Injeção, mas poderia ser tudo manual
 * caso queira.
 */
@Component
public class ValidacaoChain {

    private final Validador chain;

    @Autowired
    public ValidacaoChain(ValidaNome validaNome, ValidaApelido validaApelido, ValidaDescricao validaDescricao,
                          ValidaHistoria validaHistoria) {

        //Monta cadeia na ordem desejada
        validaNome
                .setProximo(validaApelido)
                .setProximo(validaDescricao)
                .setProximo(validaHistoria);

        //registra a cadeia
        this.chain = validaNome;
    }

    /**
     * Método que dispara a cadeia de validação
     * @param avenger
     */
    public void valida(Avenger avenger){
        List<ResponseError> erros = new ArrayList<>();
        chain.valida(avenger, erros);

        if(!erros.isEmpty()){
            throw new ValidacaoException(erros);
        }
    }
}
