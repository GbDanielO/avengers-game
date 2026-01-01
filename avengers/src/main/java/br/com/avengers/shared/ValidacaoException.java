package br.com.avengers.shared;

import java.util.List;

public class ValidacaoException extends RuntimeException {

    private final List<ResponseError> errors;

    public ValidacaoException(List<ResponseError> errors) {
        super("Erro(s) de validação");
        this.errors = errors;
    }

    public List<ResponseError> getErrors() {
        return errors;
    }
}
