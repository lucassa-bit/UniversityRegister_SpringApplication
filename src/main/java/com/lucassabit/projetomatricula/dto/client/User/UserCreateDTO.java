package com.lucassabit.projetomatricula.dto.client.User;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserCreateDTO {
    @NotBlank(message = "Erro na criação do usuario: valor em branco/nulo (nome)")
    protected String name;

    @NotBlank(message = "Erro na criação do usuario: valor em branco/nulo (email)")
    @Pattern(regexp = "^([a-zA-Z0-9]+(?:[._-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*\\.[a-zA-Z]{2,})$")
    protected String email;

    @NotBlank(message = "Erro na criação do usuario: valor em branco/nulo (identidade)")
    protected String id_number;

    @NotBlank(message = "Erro na criação do usuario: valor em branco/nulo (login)")
    protected String login;
    
    @NotBlank(message = "Erro na criação do usuario: valor em branco/nulo (email)")
    @JsonProperty(value = "password")
    protected String password;

    @NotBlank(message = "Erro na criação do usuario: valor em branco/nulo (data de nascimento)")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    protected String birthDate;

    public UserCreateDTO() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getId_number() {
        return id_number;
    }

    public String getPassword() {
        return password;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getLogin() {
        return login;
    }

}