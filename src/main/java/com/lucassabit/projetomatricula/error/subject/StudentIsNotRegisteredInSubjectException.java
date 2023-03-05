package com.lucassabit.projetomatricula.error.subject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class StudentIsNotRegisteredInSubjectException extends Exception {
    private final static String ERROR_MESSAGE = "Disciplina %s não está vinculada ao aluno(a).";
    
    public StudentIsNotRegisteredInSubjectException(String string) {
        super(String.format(ERROR_MESSAGE, string));
    }
}
