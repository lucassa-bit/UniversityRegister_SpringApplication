package com.lucassabit.projetomatricula.error.course;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class CourseDoesntExistException extends Exception {
	public static final String COURSE_DOESNT_EXIST = "Curso de %s não existe no banco de dados.";
	
	public CourseDoesntExistException(String course) {
		super(String.format(COURSE_DOESNT_EXIST, course));
	}
}
