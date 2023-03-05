package com.lucassabit.projetomatricula.service.login;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

import com.lucassabit.projetomatricula.dto.client.SubjectParticipants.SubjectParticipantsCreateDTO;
import com.lucassabit.projetomatricula.dto.client.SubjectParticipants.SubjectParticipantsEditDTO;
import com.lucassabit.projetomatricula.dto.client.User.UserCreateDTO;
import com.lucassabit.projetomatricula.dto.client.User.UserEditDTO;
import com.lucassabit.projetomatricula.dto.send.SubjectParticipantsSendDTO;
import com.lucassabit.projetomatricula.dto.send.UserSendDTO;
import com.lucassabit.projetomatricula.enumerators.UserType;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.course.CourseDoesntExistException;
import com.lucassabit.projetomatricula.error.login.DoesntExistUserTypeException;
import com.lucassabit.projetomatricula.error.login.EncodingPasswordException;
import com.lucassabit.projetomatricula.error.login.LoginAlreadyExistsException;
import com.lucassabit.projetomatricula.error.login.UserDoestExistException;
import com.lucassabit.projetomatricula.model.UserParent;

public interface UserService {
    public List<UserSendDTO> pickAllUsers();
    public List<SubjectParticipantsSendDTO> pickAllUsers(String course, UserType userT) throws CourseDoesntExistException;
    public void createNewUsuario(UserCreateDTO dto) throws LoginAlreadyExistsException, EncodingPasswordException;
    public void createNewUsuario(SubjectParticipantsCreateDTO dto, UserType userType) throws LoginAlreadyExistsException, EncodingPasswordException, CourseDoesntExistException, DoesntExistUserTypeException;
    public void editarUsuario(UserEditDTO dto) throws UserDoestExistException, LoginAlreadyExistsException, AccessDeniedException;
    public void editarUsuario(SubjectParticipantsEditDTO dto, UserType userType) throws LoginAlreadyExistsException, EncodingPasswordException, CourseDoesntExistException, UserDoestExistException, DoesntExistUserTypeException;
    public UserSendDTO getUsuarioById(Integer id, UserType userT) throws UserDoestExistException, DoesntExistUserTypeException;
    public UserSendDTO getUsuarioByLogin(String login) throws UserDoestExistException, DoesntExistUserTypeException;
    public void deleteUsuario(int id) throws AccessDeniedException, DoesntExistUserTypeException, UserDoestExistException;
    
    public static UserParent authenticated() {
        try {
            return (UserParent) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
