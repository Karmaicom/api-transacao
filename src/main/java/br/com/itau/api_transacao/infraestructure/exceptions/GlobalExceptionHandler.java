package br.com.itau.api_transacao.infraestructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnprocessableEntity.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<String> handleUnprocessableEntity(UnprocessableEntity unprocessableEntity) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Erro: " + unprocessableEntity.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleInternaServerError(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + exception.getMessage());
    }

}
