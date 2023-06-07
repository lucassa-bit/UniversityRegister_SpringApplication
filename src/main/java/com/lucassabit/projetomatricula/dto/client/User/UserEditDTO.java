package com.lucassabit.projetomatricula.dto.client.user;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;

public class UserEditDTO {
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
