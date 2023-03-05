package com.lucassabit.projetomatricula.error.login;

public class DoesntExistUserTypeException extends Exception{
    private final static String DOEST_EXIST_USER_TYPE = "Tipo de usuário selecionado não existe";

    public DoesntExistUserTypeException() {
        super(DOEST_EXIST_USER_TYPE);
    }
}
