package com.lucassabit.projetomatricula.dto.client.subject;

import java.util.List;

import javax.validation.constraints.NotNull;

public class RegisterSubjectsDTO {
    @NotNull(message = "Erro na atualização das notas: valor em branco/nulo (matricula da disciplina)")
    private List<String> registerCodeSubject;

    public RegisterSubjectsDTO() {
    }

    public List<String> getRegisterCodeSubject() {
        return registerCodeSubject;
    }

}
