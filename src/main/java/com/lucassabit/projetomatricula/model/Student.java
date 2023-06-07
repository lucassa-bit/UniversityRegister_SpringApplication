package com.lucassabit.projetomatricula.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.lucassabit.projetomatricula.dto.client.user.StudentCreateDTO;
import com.lucassabit.projetomatricula.dto.client.user.StudentEditDTO;
import com.lucassabit.projetomatricula.dto.send.StudentSendDTO;
import com.lucassabit.projetomatricula.enumerators.UserType;
import com.lucassabit.projetomatricula.error.login.EncodingPasswordException;

@Entity
@DiscriminatorValue(value = "S")
public class Student extends UserParent {
    @OneToOne
    private Worker worker;

    @OneToMany(mappedBy = "student")
    private List<Grade> subjects;

    @ManyToOne
    private Course course;
    private String registerCode;

    public Student() {
    }

    public Student(String name, String email, String id_number, String login, String password, UserType userType,
            LocalDate birthDate, PasswordEncoder pEncoder, Course course, String registerCode) {
        super(name, email, id_number, login, password, userType, birthDate, pEncoder);
        this.course = course;
        this.registerCode = registerCode;
    }

    public static Student fromCreateDto(StudentCreateDTO dto, Course course, PasswordEncoder pEncoder)
            throws EncodingPasswordException {
        return new Student(dto.getName(), dto.getEmail(), dto.getId_number(), dto.getLogin(), dto.getPassword(),
                UserType.STUDENT, LocalDate.parse(dto.getBirthDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                pEncoder, course, "");
    }

    public Student fromEditDto(StudentEditDTO dto, Course course, PasswordEncoder pEncoder) {
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

    public StudentSendDTO toSendDTO() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return new StudentSendDTO(id, name, email, id_number, login, password, userType,
                birthDate.format(format), course, registerCode);
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

    public List<Grade> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Grade> subjects) {
        this.subjects = subjects;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

}
