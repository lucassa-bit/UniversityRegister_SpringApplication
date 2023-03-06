package com.lucassabit.projetomatricula.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.lucassabit.projetomatricula.dto.client.User.UserCreateDTO;
import com.lucassabit.projetomatricula.dto.send.UserSendDTO;
import com.lucassabit.projetomatricula.enumerators.UserType;
import com.lucassabit.projetomatricula.error.login.EncodingPasswordException;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", length = 1, discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("U")
public class UserParent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    protected String name;
    protected String email;
    protected String id_number;
    protected String login;
    protected String password;

    protected UserType userType;

    protected LocalDate birthDate;

    public UserParent() {
    }

    public UserParent(Student student) {
        this(student.getName(), student.getEmail(), student.getId_number(), student.getLogin(), student.getPassword(),
                student.getUserType(), student.getBirthDate());
    }

    public UserParent(Teacher teacher) {
        this(teacher.getName(), teacher.getEmail(), teacher.getId_number(), teacher.getLogin(), teacher.getPassword(),
                teacher.getUserType(), teacher.getBirthDate());
    }

    public UserParent(Secretary secretary) {
        this(secretary.getName(), secretary.getEmail(), secretary.getId_number(), secretary.getLogin(),
                secretary.getPassword(),
                secretary.getUserType(), secretary.getBirthDate());
    }

    public UserParent(String name, String email, String id_number, String login, String password, UserType userType,
            LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.id_number = id_number;
        this.login = login;
        this.userType = userType;
        this.birthDate = birthDate;
        this.password = password;
    }

    public UserParent(String name, String email, String id_number, String login, String password, UserType userType,
            LocalDate birthDate, PasswordEncoder pEncoder) {
        this.name = name;
        this.email = email;
        this.id_number = id_number;
        this.login = login;
        this.userType = userType;
        this.birthDate = birthDate;
        this.password = pEncoder.encode(password);
    }

    public static UserParent fromCreateDto(UserCreateDTO dto, Course course, PasswordEncoder pEncoder)
            throws EncodingPasswordException {
        return new UserParent(dto.getName(), dto.getEmail(), dto.getId_number(), dto.getLogin(), dto.getPassword(),
                UserType.SECRETARY, LocalDate.parse(dto.getBirthDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    public UserParent fromEditDto(UserCreateDTO dto, Course course, PasswordEncoder pEncoder) {
        if (dto.getName() != null)
            this.name = dto.getName();
        if (dto.getBirthDate() != null)
            this.birthDate = LocalDate.parse(dto.getBirthDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        if (dto.getEmail() != null)
            this.email = dto.getEmail();
        if (dto.getId_number() != null)
            this.id_number = dto.getId_number();
        if (dto.getLogin() != null)
            this.login = dto.getLogin();
        if (dto.getPassword() != null)
            setPassword(password, pEncoder);

        return this;
    }

    public UserSendDTO toSendDTO() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return new UserSendDTO(id, name, email, id_number, userType, birthDate.format(format));
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password, PasswordEncoder pEncoder) {
        if (!password.isEmpty()) {
            this.password = pEncoder.encode(password);
        }
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Course getCourse() {
        return null;
    }
}
