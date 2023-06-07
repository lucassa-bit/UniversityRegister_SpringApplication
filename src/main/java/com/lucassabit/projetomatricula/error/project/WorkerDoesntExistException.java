package com.lucassabit.projetomatricula.error.project;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class WorkerDoesntExistException extends Exception{
    private static final String NAO_ENCONTRADA = "Estudante não possui nenhum projeto vinculado!";

    public WorkerDoesntExistException() {
        super(NAO_ENCONTRADA);
    }
}