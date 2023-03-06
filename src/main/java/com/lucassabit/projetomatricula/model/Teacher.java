package com.lucassabit.projetomatricula.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.lucassabit.projetomatricula.dto.client.SubjectParticipants.SubjectParticipantsCreateDTO;
import com.lucassabit.projetomatricula.dto.client.SubjectParticipants.SubjectParticipantsEditDTO;
import com.lucassabit.projetomatricula.dto.send.SubjectParticipantsSendDTO;
import com.lucassabit.projetomatricula.enumerators.UserType;
import com.lucassabit.projetomatricula.error.login.EncodingPasswordException;

@Entity
@DiscriminatorValue(value = "T")
public class Teacher extends UserParent {
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Subject> subjects;

    @ManyToOne
    private Course course;
    private String registerCode;

    public Teacher() {
    }

    public Teacher(String name, String email, String id_number, String login, String password, UserType userType,
            LocalDate birthDate, PasswordEncoder pEncoder, Course course) {
        super(name, email, id_number, login, password, userType, birthDate, pEncoder);
        this.course = course;
    }

    public static Teacher fromCreateDto(SubjectParticipantsCreateDTO dto, Course course, PasswordEncoder pEncoder)
            throws EncodingPasswordException {
        return new Teacher(dto.getName(), dto.getEmail(), dto.getId_number(), dto.getLogin(), dto.getPassword(),
                UserType.TEACHER, LocalDate.parse(dto.getBirthDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                pEncoder, course);
    }

    public Teacher fromEditDto(SubjectParticipantsEditDTO dto, Course course, PasswordEncoder pEncoder) {
        if (dto.getName() != null)
            this.name = dto.getName();
        if (dto.getBirthDate() != null)
            this.birthDate = LocalDate.parse(dto.getBirthDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if (dto.getCourse() != null)
            this.course = course;
        if (dto.getEmail() != null)
            this.email = dto.getEmail();
        if (dto.getId_number() != null)
            this.id_number = dto.getId_number();
        if (dto.getlogin() != null)
            this.login = dto.getlogin();
        if (dto.getPassword() != null)
            setPassword(password, pEncoder);

        return this;
    }

    public SubjectParticipantsSendDTO toSendDTO() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return new SubjectParticipantsSendDTO(id, name, email, id_number, login, password, userType, birthDate.format(format), course,
                registerCode);
    }

    public String getRegistration() {
        return registerCode;
    }

    public void setRegistration(String registerCode) {
        this.registerCode = registerCode;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
