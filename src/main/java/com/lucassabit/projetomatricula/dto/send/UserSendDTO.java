package com.lucassabit.projetomatricula.dto.send;

import com.lucassabit.projetomatricula.enumerators.UserType;

public class UserSendDTO {
    protected Integer id;
    protected String name;
    protected String email;
    protected String id_number;
    protected String password;

    protected UserType userType;
    protected String birthDate;

    public UserSendDTO() {
    }

    public UserSendDTO(Integer id, String name, String email, String id_number, UserType userType, String birthDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.id_number = id_number;
        this.userType = userType;
        this.birthDate = birthDate;
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

    public UserType getUserType() {
        return userType;
    }

    public String getBirthDate() {
        return birthDate;
    }
}
