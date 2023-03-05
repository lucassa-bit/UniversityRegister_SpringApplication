package com.lucassabit.projetomatricula.error.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class TeacherNotFoundException extends Exception {
    private final static String ERROR_MESSAGE = "Professor %s não foi encontrado no banco de dados.";
    
    public TeacherNotFoundException(String string) {
        super(String.format(ERROR_MESSAGE, string));
    }
}