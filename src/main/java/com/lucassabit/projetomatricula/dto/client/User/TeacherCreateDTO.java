package com.lucassabit.projetomatricula.dto.client.user;

import javax.validation.constraints.NotBlank;

public class TeacherCreateDTO extends UserCreateDTO{
    @NotBlank(message = "Erro na criação do usuario: valor em branco/nulo (curso)")
    private String course;

    public TeacherCreateDTO() {
    }

    public String getCourse() {
        return course;
    }
}
