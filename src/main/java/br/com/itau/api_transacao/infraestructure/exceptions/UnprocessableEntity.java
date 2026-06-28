package br.com.itau.api_transacao.infraestructure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntity extends RuntimeException {

    private String message;

    public UnprocessableEntity(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
