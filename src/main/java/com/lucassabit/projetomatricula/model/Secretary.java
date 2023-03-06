package com.lucassabit.projetomatricula.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.lucassabit.projetomatricula.dto.client.User.UserCreateDTO;
import com.lucassabit.projetomatricula.dto.client.User.UserEditDTO;
import com.lucassabit.projetomatricula.dto.send.UserSendDTO;
import com.lucassabit.projetomatricula.enumerators.UserType;
import com.lucassabit.projetomatricula.error.login.EncodingPasswordException;

@Entity
@DiscriminatorValue(value = "E")
public class Secretary extends UserParent {

    public Secretary(String name, String email, String id_number, String login, String password, UserType userType,
            LocalDate birthDate, PasswordEncoder pEncoder) {
        super(name, email, id_number, login, password, userType, birthDate, pEncoder);
    }

    public Secretary() {
    }

    public static Secretary fromCreateDto(UserCreateDTO dto, PasswordEncoder pEncoder)
            throws EncodingPasswordException {
        return new Secretary(dto.getName(), dto.getEmail(), dto.getId_number(), dto.getLogin(), dto.getPassword(),
                UserType.SECRETARY, LocalDate.parse(dto.getBirthDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                pEncoder);
    }

    public Secretary fromEditDto(UserEditDTO dto, PasswordEncoder pEncoder) {
        if (dto.getName() != null)
            this.name = dto.getName();
        if (dto.getBirthDate() != null)
            this.birthDate = LocalDate.parse(dto.getBirthDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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

    public UserSendDTO toSendDTO() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return new UserSendDTO(id, name, email, id_number, userType, birthDate.format(format));
    }

}
