package br.com.avengers.domain.validation;

import br.com.avengers.adapters.out.persistence.entity.Vilao;
import br.com.avengers.shared.ResponseError;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

@Component
@Order(2)
public class ValidaNome implements  Validador{

    private static final String REGEX_NOME = "^[a-zA-Z0-9À-ÿ\\s\\-\\.\\'\\&]+$";
    private static final Pattern PATTERN = Pattern.compile(REGEX_NOME);

    @Override
    public void valida(Vilao vilao, List<ResponseError> erros) {
        if(!PATTERN.matcher(vilao.getNomeReal()).matches()){
            erros.add(
                    new ResponseError("Verifique os caracteres usados no nome do personagem. " +
                            "Caracteres especiais não são permitidos (#%$@...)"));
        }

    }

}
