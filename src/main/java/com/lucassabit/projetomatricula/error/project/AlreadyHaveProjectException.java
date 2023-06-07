package com.lucassabit.projetomatricula.error.project;

import com.lucassabit.projetomatricula.model.Teacher;

public class AlreadyHaveProjectException extends Exception {
    public static final String MESSAGE = "Professor %s já possui um projeto, sendo ele: %s";

    public AlreadyHaveProjectException(Teacher teacher) {
        super(String.format(MESSAGE, teacher.getName(), teacher.getProject().getName()));
    }
}
