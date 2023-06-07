package com.lucassabit.projetomatricula.dto.client.project;

import javax.validation.constraints.NotBlank;

public class ProjectCreateDTO {
    @NotBlank(message = "Erro na criação do projeto: valor em branco/nulo (nome)")
    private String name;
    @NotBlank(message = "Erro na criação do projeto: valor em branco/nulo (descrição)")
    private String description;

    public ProjectCreateDTO() {}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
