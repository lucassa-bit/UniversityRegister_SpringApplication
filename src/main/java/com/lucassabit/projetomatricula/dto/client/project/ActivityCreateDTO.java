package com.lucassabit.projetomatricula.dto.client.project;

import javax.validation.constraints.NotBlank;

public class ActivityCreateDTO {
    @NotBlank(message = "Erro na criação da atividade: valor em branco/nulo (descrição)")
    private String description;

    public ActivityCreateDTO() {
    }

    public String getDescription() {
        return description;
    }
}
