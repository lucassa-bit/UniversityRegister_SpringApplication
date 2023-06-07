package com.lucassabit.projetomatricula.dto.client.user;

import com.lucassabit.projetomatricula.enumerators.UserType;

public class TeacherEditDTO extends UserEditDTO {
    private UserType userType;
    private String course;

    public TeacherEditDTO() {
    }

    public String getCourse() {
        return course;
    }

    public UserType getUserType() {
        return userType;
    }
}
