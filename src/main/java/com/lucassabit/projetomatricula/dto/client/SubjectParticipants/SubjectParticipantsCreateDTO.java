package com.lucassabit.projetomatricula.dto.client.SubjectParticipants;

import javax.validation.constraints.NotBlank;

import com.lucassabit.projetomatricula.dto.client.User.UserCreateDTO;

public class SubjectParticipantsCreateDTO extends UserCreateDTO {
    @NotBlank(message = "Erro na criação do usuario: valor em branco/nulo (curso)")
    private String course;

    public SubjectParticipantsCreateDTO() {
    }

    public String getCourse() {
        return course;
    }
}
