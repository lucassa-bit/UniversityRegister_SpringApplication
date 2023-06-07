package com.lucassabit.projetomatricula.error.project;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)

public class ActivityDoesntExistException extends Exception {
    private static final String NAO_ENCONTRADA = "Atividade com id %d não foi encontrada";

    public ActivityDoesntExistException(int id) {
        super(String.format(NAO_ENCONTRADA, id));
    }
}
