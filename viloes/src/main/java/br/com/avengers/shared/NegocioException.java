package br.com.avengers.shared;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NegocioException extends RuntimeException {

    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public NegocioException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public NegocioException(String message, Object... params) {
        super(String.format(message, params));
    }
}
