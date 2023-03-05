package com.lucassabit.projetomatricula.dto.client.Course;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CourseEditDTO {
    @NotNull(message = "Erro na edição do curso: valor em branco/nulo (id)")
    public Integer id;
    @NotBlank(message = "Erro na edição do curso: valor em branco/nulo (nome)")
    public String name;

    public CourseEditDTO() {
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

}
