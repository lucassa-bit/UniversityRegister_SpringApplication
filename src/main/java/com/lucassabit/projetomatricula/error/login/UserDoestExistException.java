package com.lucassabit.projetomatricula.error.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserDoestExistException extends Exception {
	public static final String USUARIO_NAO_EXISTE = "Usuario %s não existe.";
	
	public UserDoestExistException(String usuario) {
		super(String.format(USUARIO_NAO_EXISTE, usuario));
	}
}
