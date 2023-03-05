package com.lucassabit.projetomatricula.dto.send;

import com.lucassabit.projetomatricula.enumerators.UserType;
import com.lucassabit.projetomatricula.model.Course;

public class SubjectParticipantsSendDTO extends UserSendDTO {
    private String course;
    private String registration;

    public SubjectParticipantsSendDTO(Integer id, String name, String email, String id_number, String login,
            String password, UserType userType, String birthDate, Course course, String registration) {
        super(id, name, email, id_number, userType, birthDate);
        this.course = course.getName();
        this.registration = registration;
    }

    public String getCourse() {
        return course;
    }

    public String getRegistration() {
        return registration;
    }
}
