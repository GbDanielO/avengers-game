package br.com.avengers.shared;

import jakarta.annotation.Resource;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Locale;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private HttpHeaders headers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private ResponseError responseError(String message, HttpStatus httpStatusCode){
        return new ResponseError(message, httpStatusCode.value(), "error");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerGeral(Exception e, WebRequest request){
        System.out.println(e.getMessage());
        e.printStackTrace();
        if(e.getClass().isAssignableFrom(UndeclaredThrowableException.class)){
            UndeclaredThrowableException exception = (UndeclaredThrowableException) e;
            return  handleBusinessException((NegocioException) exception.getUndeclaredThrowable(), request);
        } else {
            String message = "Erro interno no Servidor: " + e.getMessage();
            ResponseError responseError = responseError(message, HttpStatus.INTERNAL_SERVER_ERROR);
            return handleExceptionInternal(e, responseError, headers(), HttpStatus.INTERNAL_SERVER_ERROR, request);
        }
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleBusinessException(NegocioException negocioException, WebRequest request) {
        ResponseError responseError = responseError(negocioException.getMessage(), negocioException.getStatus());
        return handleExceptionInternal(negocioException, responseError, headers(), negocioException.getStatus(), request);
    }
}
