package com.lucassabit.projetomatricula.dto.client.course;

import javax.validation.constraints.NotBlank;

public class CourseCreateDTO {
    @NotBlank(message = "Erro na criação do curso: valor em branco/nulo (nome)")
    public String name;

    public CourseCreateDTO() {}

    public String getName() {
        return name;
    }
}
