package com.lucassabit.projetomatricula.error.course;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CourseAlreadyExistsException extends Exception{
    public static final String COURSE_DOESNT_EXIST = "Curso de %s já existe no banco de dados.";
	
	public CourseAlreadyExistsException(String course) {
		super(String.format(COURSE_DOESNT_EXIST, course));
	}
}
