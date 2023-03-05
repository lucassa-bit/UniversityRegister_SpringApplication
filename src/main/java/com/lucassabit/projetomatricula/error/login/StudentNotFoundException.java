package com.lucassabit.projetomatricula.error.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class StudentNotFoundException extends Exception {
    private final static String ERROR_MESSAGE = "Estudante %s não foi encontrado no banco de dados.";
    
    public StudentNotFoundException(String string) {
        super(String.format(ERROR_MESSAGE, string));
    }
}