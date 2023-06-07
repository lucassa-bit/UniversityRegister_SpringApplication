package com.lucassabit.projetomatricula.dto.client.user;

import com.lucassabit.projetomatricula.enumerators.UserType;

public class StudentEditDTO extends UserEditDTO {
    private UserType userType;
    private String course;

    public StudentEditDTO() {
    }

    public String getCourse() {
        return course;
    }

    public UserType getUserType() {
        return userType;
    }
}
