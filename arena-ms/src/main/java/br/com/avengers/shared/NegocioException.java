package br.com.avengers.shared;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class NegocioException extends RuntimeException {

    public NegocioException(String message) {
        super(message);
    }

    public NegocioException(List<String> messages) {
        super(String.join("\n", messages));
    }

}
