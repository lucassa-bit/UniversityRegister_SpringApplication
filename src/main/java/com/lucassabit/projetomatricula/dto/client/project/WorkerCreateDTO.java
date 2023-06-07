package com.lucassabit.projetomatricula.dto.client.project;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.lucassabit.projetomatricula.enumerators.WorkerLevel;

public class WorkerCreateDTO {
    @NotBlank(message = "Erro na criação do perfil de trabalhador do estudante: valor em branco/nulo (função)")
    private String student_function;
    @NotNull(message = "Erro na criação do perfil de trabalhador do estudante: valor em branco/nulo (nível do estudante)")
    private WorkerLevel student_level;

    public WorkerCreateDTO(String student_registerCode, int project_id, String student_function,
            WorkerLevel student_level) {
        this.student_function = student_function;
        this.student_level = student_level;
    }

    public String getStudent_function() {
        return student_function;
    }

    public WorkerLevel getStudent_level() {
        return student_level;
    }
}
