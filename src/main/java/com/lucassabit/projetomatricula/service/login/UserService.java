package com.lucassabit.projetomatricula.service.login;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

import com.lucassabit.projetomatricula.dto.client.user.StudentCreateDTO;
import com.lucassabit.projetomatricula.dto.client.user.StudentEditDTO;
import com.lucassabit.projetomatricula.dto.client.user.TeacherCreateDTO;
import com.lucassabit.projetomatricula.dto.client.user.TeacherEditDTO;
import com.lucassabit.projetomatricula.dto.client.user.UserCreateDTO;
import com.lucassabit.projetomatricula.dto.client.user.UserEditDTO;
import com.lucassabit.projetomatricula.dto.send.StudentSendDTO;
import com.lucassabit.projetomatricula.dto.send.TeacherSendDTO;
import com.lucassabit.projetomatricula.dto.send.UserSendDTO;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.course.CourseDoesntExistException;
import com.lucassabit.projetomatricula.error.login.DoesntExistUserTypeException;
import com.lucassabit.projetomatricula.error.login.EncodingPasswordException;
import com.lucassabit.projetomatricula.error.login.LoginAlreadyExistsException;
import com.lucassabit.projetomatricula.error.login.UserDoestExistException;
import com.lucassabit.projetomatricula.model.UserParent;

public interface UserService {
    public List<UserSendDTO> pickAllSecretaries();

    public List<StudentSendDTO> pickAllStudents(String course);

    public List<TeacherSendDTO> pickAllTeachers(String course);

    public void createNewSecretary(UserCreateDTO dto) throws LoginAlreadyExistsException, EncodingPasswordException;

    public void createNewStudent(StudentCreateDTO dto) throws EncodingPasswordException, LoginAlreadyExistsException, CourseDoesntExistException;

    public void createNewTeacher(TeacherCreateDTO dto) throws CourseDoesntExistException, LoginAlreadyExistsException, EncodingPasswordException;

    public void editSecretary(UserEditDTO dto, int id_user) throws AccessDeniedException, UserDoestExistException, LoginAlreadyExistsException;

    public void editStudent(StudentEditDTO dto, int id_user) throws LoginAlreadyExistsException, UserDoestExistException, CourseDoesntExistException;

    public void editTeacher(TeacherEditDTO dto, int id_user) throws CourseDoesntExistException, UserDoestExistException, LoginAlreadyExistsException;

    public UserSendDTO getSecretaryById(Integer id) throws UserDoestExistException;

    public TeacherSendDTO getTeacherById(Integer id) throws UserDoestExistException;

    public StudentSendDTO getStudentById(Integer id) throws UserDoestExistException;

    public UserSendDTO getUsuarioByLogin(String login) throws UserDoestExistException, DoesntExistUserTypeException;

    public void deleteSecretary(int id) throws UserDoestExistException;

    public void deleteStudent(int id) throws UserDoestExistException;

    public void deleteTeacher(int id) throws UserDoestExistException;

    public static UserParent authenticated() {
        try {
            return (UserParent) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
