package br.com.avengers.domain.validation;

import br.com.avengers.domain.model.Batalha;
import br.com.avengers.shared.NegocioException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValidacaoChain {

    private Validador chain;

    public ValidacaoChain(ValidaPersonagem validaPersonagem, ValidaHabilidade validaHabilidade){

        // Monta a cadeia
        validaPersonagem.setProximo(validaHabilidade);

        // Registra a cadeia
        chain = validaPersonagem;


    }

    /**
     * Método que dispara a cadeia de validação
     */
    public void valida(Batalha batalha){
        List<String> erros = new ArrayList<>();
        chain.valida(batalha, erros);

        if(!erros.isEmpty()){
            throw new NegocioException(erros);
        }
    }

}
