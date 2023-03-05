package com.lucassabit.projetomatricula.dto.client.SubjectParticipants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.lucassabit.projetomatricula.dto.client.User.UserEditDTO;
import com.lucassabit.projetomatricula.enumerators.UserType;

public class SubjectParticipantsEditDTO extends UserEditDTO{
    @NotNull(message = "Erro na criação do usuario: valor nulo (cargo)")
    private UserType userType;
    @NotBlank(message = "Erro na edição do usuario: valor em branco/nulo (curso)")
    private String course;

    public SubjectParticipantsEditDTO() {}

    public String getCourse() {
        return course;
    }
}
