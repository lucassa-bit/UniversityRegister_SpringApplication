package com.lucassabit.projetomatricula.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lucassabit.projetomatricula.dto.client.subject.ChangeGradesDTO;
import com.lucassabit.projetomatricula.dto.client.subject.RegisterSubjectsDTO;
import com.lucassabit.projetomatricula.dto.client.subject.SubjectCreateDTO;
import com.lucassabit.projetomatricula.dto.client.subject.SubjectEditDTO;
import com.lucassabit.projetomatricula.dto.send.SubjectSendDTO;
import com.lucassabit.projetomatricula.dto.send.SubjectStudentSendDTO;
import com.lucassabit.projetomatricula.enumerators.UserType;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.course.CourseDoesntExistException;
import com.lucassabit.projetomatricula.error.login.DoesntExistUserTypeException;
import com.lucassabit.projetomatricula.error.login.StudentNotFoundException;
import com.lucassabit.projetomatricula.error.login.TeacherNotFoundException;
import com.lucassabit.projetomatricula.error.login.UserDoestExistException;
import com.lucassabit.projetomatricula.error.subject.StudentIsNotRegisteredInSubjectException;
import com.lucassabit.projetomatricula.error.subject.SubjectDoesntExistException;
import com.lucassabit.projetomatricula.service.login.PermissionsVerifyService;
import com.lucassabit.projetomatricula.service.subject.SubjectService;

import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
        @Autowired
        public SubjectService sService;
        @Autowired
        public PermissionsVerifyService vService;

        private final String SUBJECT_CREATED = "Cadastro da disciplina realizada com sucesso!";
        private final String SUBJECT_EDITED = "Edicao de disciplina realizada com sucesso!";
        private final String SUBJECT_DELETED = "Delete de disciplina realizada com sucesso!";
        private final String SUBJECT_STUDENT_ADDED = "Adicão do estudante foi realizada com sucesso!";
        private final String SUBJECT_GRADES = "A alteração das notas foi realizada com sucesso!";

        @PostMapping("/course/{course_name}/teacher/{register_code}")
        @ResponseStatus(code = HttpStatus.CREATED)
        public ResponseEntity<String> createNewSubject(Authentication authentication,
                        @Valid @RequestBody SubjectCreateDTO dto, @PathVariable String register_code,
                        @PathVariable String course_name)
                        throws TeacherNotFoundException, CourseDoesntExistException, UserDoestExistException,
                        AccessDeniedException,
                        DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                sService.createSubject(dto, register_code, course_name);

                return new ResponseEntity<String>(SUBJECT_CREATED, HttpStatus.CREATED);
        }

        @GetMapping("/course/{course}")
        public List<SubjectSendDTO> getAllSubjects(Authentication authentication, @PathVariable String course)
                        throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY, UserType.STUDENT, UserType.TEACHER });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                return sService.getAllSubjects(course);
        }

        @GetMapping("/student/{register_code}")
        public List<SubjectStudentSendDTO> getStudentSubjects(Authentication authentication,
                        @PathVariable String register_code)
                        throws StudentNotFoundException, UserDoestExistException, AccessDeniedException,
                        DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY, UserType.STUDENT });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                return sService.getSubjectFromStudent(register_code);
        }

        @GetMapping("/teacher/{register_code}")
        public List<SubjectSendDTO> getTeacherSubjects(Authentication authentication,
                        @PathVariable String register_code)
                        throws StudentNotFoundException, UserDoestExistException, AccessDeniedException,
                        DoesntExistUserTypeException, TeacherNotFoundException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY, UserType.TEACHER });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                return sService.getSubjectFromTeacher(register_code);
        }

        @PatchMapping("/add/student/{register_code}")
        public ResponseEntity<String> addingStudent(Authentication authentication,
                        @Valid @RequestBody RegisterSubjectsDTO dto, @PathVariable String register_code)
                        throws SubjectDoesntExistException, CourseDoesntExistException, TeacherNotFoundException,
                        StudentNotFoundException, UserDoestExistException, AccessDeniedException,
                        DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY, UserType.STUDENT });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                sService.readjusmentRegister(dto, register_code);

                return new ResponseEntity<String>(SUBJECT_STUDENT_ADDED, HttpStatus.OK);
        }

        @PatchMapping("/grades/subject/{register_code}")
        public ResponseEntity<String> changingGrades(Authentication authentication,
                        @Valid @RequestBody List<ChangeGradesDTO> dto, @PathVariable String register_code)
                        throws SubjectDoesntExistException, CourseDoesntExistException, TeacherNotFoundException,
                        StudentNotFoundException, StudentIsNotRegisteredInSubjectException, UserDoestExistException,
                        AccessDeniedException, DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.TEACHER });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                for (ChangeGradesDTO elements : dto) {
                        sService.changeGrades(elements, register_code);
                }

                return new ResponseEntity<String>(SUBJECT_GRADES, HttpStatus.OK);
        }

        @PatchMapping("/{id}")
        public ResponseEntity<String> editSubject(Authentication authentication, @Valid @RequestBody SubjectEditDTO dto,
                        @PathVariable int id)
                        throws SubjectDoesntExistException, CourseDoesntExistException, TeacherNotFoundException,
                        UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                sService.editSubject(dto, id);

                return new ResponseEntity<String>(SUBJECT_EDITED, HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteCourse(Authentication authentication, @PathVariable int id)
                        throws SubjectDoesntExistException, UserDoestExistException, AccessDeniedException,
                        DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                sService.deleteSubject(id);

                return new ResponseEntity<String>(SUBJECT_DELETED, HttpStatus.OK);
        }
}
