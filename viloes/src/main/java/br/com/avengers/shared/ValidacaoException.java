package br.com.avengers.shared;

import java.util.List;

public class ValidacaoException extends RuntimeException{

    private final List<ResponseError> erros;

    public ValidacaoException(List<ResponseError> erros) {
        super("Erro(s) de validação");
        this.erros = erros;
    }

    public List<ResponseError> getErros() {
        return erros;
    }
}
