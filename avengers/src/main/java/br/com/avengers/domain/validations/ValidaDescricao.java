package br.com.avengers.domain.validations;

import br.com.avengers.adapters.out.persistence.entity.Avenger;
import br.com.avengers.shared.ResponseError;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class ValidaDescricao extends AbstractValidador {

    private static final String REGEX_NOME = "^[a-zA-Z0-9À-ÿ\\s\\-\\.\\'\\&]+$";
    private static final Pattern PATTERN = Pattern.compile(REGEX_NOME);

    @Override
    public void valida(Avenger avenger, List<ResponseError> erros) {
        if(!PATTERN.matcher(avenger.getDescricao()).matches()){
            erros.add(
                    new ResponseError("Verifique os caracteres usados na descrição do personagem. " +
                            "Caracteres especiais não são permitidos (#%$@...)"));
        }

        chamaProximo(avenger, erros);
    }

}
