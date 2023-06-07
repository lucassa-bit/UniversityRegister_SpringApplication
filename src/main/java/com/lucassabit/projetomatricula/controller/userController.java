package com.lucassabit.projetomatricula.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lucassabit.projetomatricula.dto.client.user.StudentCreateDTO;
import com.lucassabit.projetomatricula.dto.client.user.StudentEditDTO;
import com.lucassabit.projetomatricula.dto.client.user.TeacherCreateDTO;
import com.lucassabit.projetomatricula.dto.client.user.TeacherEditDTO;
import com.lucassabit.projetomatricula.dto.client.user.UserCreateDTO;
import com.lucassabit.projetomatricula.dto.client.user.UserEditDTO;
import com.lucassabit.projetomatricula.dto.send.StudentSendDTO;
import com.lucassabit.projetomatricula.dto.send.TeacherSendDTO;
import com.lucassabit.projetomatricula.dto.send.UserSendDTO;
import com.lucassabit.projetomatricula.enumerators.UserType;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.course.CourseDoesntExistException;
import com.lucassabit.projetomatricula.error.login.DoesntExistUserTypeException;
import com.lucassabit.projetomatricula.error.login.EncodingPasswordException;
import com.lucassabit.projetomatricula.error.login.LoginAlreadyExistsException;
import com.lucassabit.projetomatricula.error.login.UserDoestExistException;
import com.lucassabit.projetomatricula.service.login.PermissionsVerifyService;
import com.lucassabit.projetomatricula.service.login.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {
        @Autowired
        private UserService uServices;
        @Autowired
        public PermissionsVerifyService vService;

        private final String USER_CREATED = "Cadastro do usuario realizado com sucesso!";
        private final String USER_EDITED = "Edicao de usuario realizado com sucesso!";
        private final String USER_DELETED = "Delete de usuario realizado com sucesso!";

        @GetMapping("/me")
        public UserSendDTO getUsuarioLogado(Authentication authentication)
                        throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException {

                return uServices.getUsuarioByLogin(authentication.getName());
        }

        @PostMapping("/secretary")
        @ResponseStatus(code = HttpStatus.CREATED)
        public ResponseEntity<String> createNewSecretary(Authentication authentication,
                        @Valid @RequestBody UserCreateDTO dto)
                        throws EncodingPasswordException, LoginAlreadyExistsException {
                if (dto.getPassword() == null)
                        throw new EncodingPasswordException("Senha possui valor nulo");

                uServices.createNewSecretary(dto);

                return new ResponseEntity<String>(USER_CREATED, HttpStatus.CREATED);
        }

        @GetMapping("/secretary")
        public List<UserSendDTO> getAllSecretary(Authentication authentication)
                        throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                return uServices.pickAllSecretaries();
        }

        @PatchMapping("/secretary/{id}")
        public ResponseEntity<String> editSecretary(Authentication authentication, @Valid @RequestBody UserEditDTO dto,
                        @PathVariable int id) throws UserDoestExistException, AccessDeniedException,
                        DoesntExistUserTypeException, LoginAlreadyExistsException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                uServices.editSecretary(dto, id);

                return new ResponseEntity<String>(USER_EDITED, HttpStatus.OK);
        }

        @PostMapping("/teacher")
        @ResponseStatus(code = HttpStatus.CREATED)
        public ResponseEntity<String> createNewTeacher(@Valid @RequestBody TeacherCreateDTO dto)
                        throws EncodingPasswordException, CourseDoesntExistException, LoginAlreadyExistsException {

                if (dto.getPassword() == null)
                        throw new EncodingPasswordException("Senha possui valor nulo");

                uServices.createNewTeacher(dto);

                return new ResponseEntity<String>(USER_CREATED, HttpStatus.CREATED);
        }

        @GetMapping("/teacher/course/{course}")
        public List<TeacherSendDTO> getAllTeachersByCourse(Authentication authentication, @PathVariable String course)
                        throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                return uServices.pickAllTeachers(course);
        }

        @PatchMapping("/teacher/{id}")
        public ResponseEntity<String> editTeacher(Authentication authentication,
                        @Valid @RequestBody TeacherEditDTO dto, @PathVariable int id)
                        throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException,
                        CourseDoesntExistException, LoginAlreadyExistsException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);
                uServices.editTeacher(dto, id);

                return new ResponseEntity<String>(USER_EDITED, HttpStatus.OK);
        }

        @PostMapping("/student")
        @ResponseStatus(code = HttpStatus.CREATED)
        public ResponseEntity<String> createNewStudent(Authentication authentication,
                        @Valid @RequestBody StudentCreateDTO dto)
                        throws EncodingPasswordException, LoginAlreadyExistsException, CourseDoesntExistException {
                if (dto.getPassword() == null)
                        throw new EncodingPasswordException("Senha possui valor nulo");

                uServices.createNewStudent(dto);

                return new ResponseEntity<String>(USER_CREATED, HttpStatus.CREATED);
        }

        @GetMapping("/student/course/{course}")
        public List<StudentSendDTO> getAllStudentsByCourse(Authentication authentication,
                        @PathVariable String course)
                        throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY, UserType.TEACHER });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                return uServices.pickAllStudents(course);
        }

        @PatchMapping("/student/{id}")
        public ResponseEntity<String> editStudent(Authentication authentication,
                        @Valid @RequestBody StudentEditDTO dto, @PathVariable int id)
                        throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException,
                        LoginAlreadyExistsException, CourseDoesntExistException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY, UserType.STUDENT });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                uServices.editStudent(dto, id);

                return new ResponseEntity<String>(USER_EDITED, HttpStatus.OK);
        }

        @GetMapping("/secretary/{id}")
        public UserSendDTO getSecretaryById(Authentication authentication, @PathVariable int id)
                        throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY, UserType.TEACHER });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                return uServices.getSecretaryById(id);
        }

        @GetMapping("/teacher/{id}")
        public UserSendDTO getTeacherById(Authentication authentication, @PathVariable int id)
                        throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY, UserType.TEACHER });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                return uServices.getTeacherById(id);
        }

        @GetMapping("/student/{id}")
        public UserSendDTO getStudentById(Authentication authentication, @PathVariable int id)
                        throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY, UserType.TEACHER });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                return uServices.getStudentById(id);
        }

        @DeleteMapping("/secretary/{id}")
        public ResponseEntity<String> deleteSecretary(Authentication authentication, @PathVariable Integer id)
                        throws AccessDeniedException, UserDoestExistException, DoesntExistUserTypeException {
                if (id == 1)
                        throw new AccessDeniedException();

                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                uServices.deleteSecretary(id);

                return new ResponseEntity<String>(USER_DELETED, HttpStatus.OK);
        }

        @DeleteMapping("/teacher/{id}")
        public ResponseEntity<String> deleteTeacher(Authentication authentication, @PathVariable Integer id)
                        throws AccessDeniedException, UserDoestExistException, DoesntExistUserTypeException {
                if (id == 1)
                        throw new AccessDeniedException();

                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                uServices.deleteTeacher(id);

                return new ResponseEntity<String>(USER_DELETED, HttpStatus.OK);
        }

        @DeleteMapping("/student/{id}")
        public ResponseEntity<String> deleteStudent(Authentication authentication, @PathVariable Integer id)
                        throws AccessDeniedException, UserDoestExistException, DoesntExistUserTypeException {
                if (id == 1)
                        throw new AccessDeniedException();

                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                uServices.deleteStudent(id);

                return new ResponseEntity<String>(USER_DELETED, HttpStatus.OK);
        }
}
