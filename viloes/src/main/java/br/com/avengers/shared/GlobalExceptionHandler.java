package br.com.avengers.shared;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.UndeclaredThrowableException;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private HttpHeaders headers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private ResponseError responseError(String message, HttpStatus httpStatusCode){
        return new ResponseError("error", httpStatusCode.value(), message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerGeral(Exception e, WebRequest request){

        log.error("Erro interno no Servidor", e);

        ResponseError responseError = responseError("Erro interno no Servidor: ", HttpStatus.INTERNAL_SERVER_ERROR);
        return handleExceptionInternal(e, responseError, headers(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleBusinessException(NegocioException negocioException, WebRequest request) {

        log.warn("Erro de negócio: {}", negocioException.getMessage());

        ResponseError responseError = responseError(negocioException.getMessage(), negocioException.getStatus());
        return handleExceptionInternal(negocioException, responseError, headers(), negocioException.getStatus(), request);
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<Object> handleValidationException(
            ValidacaoException validacaoException, WebRequest request) {

        log.warn("Erro de validação: {} erro(s)", validacaoException.getErros().size());

        return handleExceptionInternal(
                validacaoException,
                validacaoException.getErros(),
                headers(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }
}
