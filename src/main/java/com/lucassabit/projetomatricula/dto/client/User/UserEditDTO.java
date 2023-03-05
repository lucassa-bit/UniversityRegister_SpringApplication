package com.lucassabit.projetomatricula.dto.client.User;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserEditDTO {
    @NotNull(message = "Erro na edição do Usuário: usuário não existe")
    private Integer id;

    protected String name;

    @Pattern(regexp = "^([a-zA-Z0-9]+(?:[._-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*\\.[a-zA-Z]{2,})$")
    protected String email;

    protected String id_number;

    protected String login;
    protected String password;


    @DateTimeFormat(pattern = "dd/MM/yyyy")
    protected String birthDate;

    public UserEditDTO() {
    }

    public Integer getId() {
        return id;
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

    public String getlogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }



    public String getBirthDate() {
        return birthDate;
    }

}
