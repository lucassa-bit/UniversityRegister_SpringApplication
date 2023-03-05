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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lucassabit.projetomatricula.dto.client.Subject.ChangeGradesDTO;
import com.lucassabit.projetomatricula.dto.client.Subject.RegisterSubjectsDTO;
import com.lucassabit.projetomatricula.dto.client.Subject.SubjectCreateDTO;
import com.lucassabit.projetomatricula.dto.client.Subject.SubjectEditDTO;
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

        @PostMapping
        @ResponseStatus(code = HttpStatus.CREATED)
        public ResponseEntity<String> createNewSubject(Authentication authentication,
                        @Valid @RequestBody SubjectCreateDTO dto)
                        throws TeacherNotFoundException, CourseDoesntExistException, UserDoestExistException,
                        AccessDeniedException,
                        DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                sService.createSubject(dto);

                return new ResponseEntity<String>(SUBJECT_CREATED, HttpStatus.CREATED);
        }

        @GetMapping
        public List<SubjectSendDTO> getAllSubjects(Authentication authentication, @RequestParam String course)
                        throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY, UserType.STUDENT, UserType.TEACHER });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                return sService.getAllSubjects(course);
        }

        @GetMapping("/findByStudent")
        public List<SubjectStudentSendDTO> getStudentSubjects(Authentication authentication,
                        @RequestParam String registrationCode)
                        throws StudentNotFoundException, UserDoestExistException, AccessDeniedException,
                        DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY, UserType.STUDENT });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                return sService.getSubjectFromStudent(registrationCode);
        }

        @GetMapping("/findByTeacher")
        public List<SubjectSendDTO> getTeacherSubjects(Authentication authentication,
                        @RequestParam String registrationCode)
                        throws StudentNotFoundException, UserDoestExistException, AccessDeniedException,
                        DoesntExistUserTypeException, TeacherNotFoundException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY, UserType.TEACHER });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                return sService.getSubjectFromTeacher(registrationCode);
        }

        @PutMapping("/add")
        public ResponseEntity<String> addingStudent(Authentication authentication,
                        @Valid @RequestBody RegisterSubjectsDTO dto)
                        throws SubjectDoesntExistException, CourseDoesntExistException, TeacherNotFoundException,
                        StudentNotFoundException, UserDoestExistException, AccessDeniedException,
                        DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY, UserType.STUDENT });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                sService.readjusmentRegister(dto);

                return new ResponseEntity<String>(SUBJECT_STUDENT_ADDED, HttpStatus.OK);
        }

        @PutMapping("/edit_grades")
        public ResponseEntity<String> changingGrades(Authentication authentication,
                        @Valid @RequestBody List<ChangeGradesDTO> dto)
                        throws SubjectDoesntExistException, CourseDoesntExistException, TeacherNotFoundException,
                        StudentNotFoundException, StudentIsNotRegisteredInSubjectException, UserDoestExistException,
                        AccessDeniedException, DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.TEACHER });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                for (ChangeGradesDTO elements : dto) {
                        sService.changeGrades(elements);
                }

                return new ResponseEntity<String>(SUBJECT_GRADES, HttpStatus.OK);
        }

        @PutMapping
        public ResponseEntity<String> editCourse(Authentication authentication, @Valid @RequestBody SubjectEditDTO dto)
                        throws SubjectDoesntExistException, CourseDoesntExistException, TeacherNotFoundException,
                        UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                sService.editSubject(dto);

                return new ResponseEntity<String>(SUBJECT_EDITED, HttpStatus.OK);
        }

        @DeleteMapping
        public ResponseEntity<String> deleteCourse(Authentication authentication, @RequestParam int id)
                        throws SubjectDoesntExistException, UserDoestExistException, AccessDeniedException,
                        DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.SECRETARY });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);

                sService.deleteSubject(id);

                return new ResponseEntity<String>(SUBJECT_DELETED, HttpStatus.OK);
        }
}
