package com.lucassabit.projetomatricula.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class AccessDeniedException extends Exception {
	private static final String ACESSO_NEGADO = "Acesso negado.";
	
	public AccessDeniedException() {
		super(ACESSO_NEGADO);
	}
}
