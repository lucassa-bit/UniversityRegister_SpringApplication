package com.lucassabit.projetomatricula.controller;

import java.util.Arrays;
import java.util.List;

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

import org.springframework.security.core.Authentication;

import com.lucassabit.projetomatricula.dto.client.Course.CourseCreateDTO;
import com.lucassabit.projetomatricula.dto.client.Course.CourseEditDTO;
import com.lucassabit.projetomatricula.dto.send.CourseSendDTO;
import com.lucassabit.projetomatricula.enumerators.UserType;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.course.CourseAlreadyExistsException;
import com.lucassabit.projetomatricula.error.course.CourseDoesntExistException;
import com.lucassabit.projetomatricula.error.login.DoesntExistUserTypeException;
import com.lucassabit.projetomatricula.error.login.UserDoestExistException;
import com.lucassabit.projetomatricula.service.course.CourseService;
import com.lucassabit.projetomatricula.service.login.PermissionsVerifyService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    public CourseService cService;
    @Autowired
    public PermissionsVerifyService vService;

    private final String COURSE_CREATED = "Cadastro do curso realizado com sucesso!";
    private final String COURSE_EDITED = "Edicao de curso realizado com sucesso!";
    private final String COURSE_DELETED = "Delete de curso realizado com sucesso!";

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> createNewUsuario(Authentication authentication,
            @Valid @RequestBody CourseCreateDTO dto)
            throws CourseAlreadyExistsException, UserDoestExistException, DoesntExistUserTypeException,
            AccessDeniedException {
        List<UserType> cargosPermitidos = Arrays
                .asList(new UserType[] { UserType.SECRETARY });
        vService.PermissionVerify(authentication.getName(), cargosPermitidos);
        cService.CreateNewCourse(dto);

        return new ResponseEntity<String>(COURSE_CREATED, HttpStatus.CREATED);
    }

    @GetMapping
    public List<CourseSendDTO> getAllCourses() {
        return cService.getAllCourses();
    }

    @PutMapping
    public ResponseEntity<String> editCourse(Authentication authentication, @Valid @RequestBody CourseEditDTO dto)
            throws CourseAlreadyExistsException, CourseDoesntExistException, UserDoestExistException,
            AccessDeniedException, DoesntExistUserTypeException {
        List<UserType> cargosPermitidos = Arrays
                .asList(new UserType[] { UserType.SECRETARY });
        vService.PermissionVerify(authentication.getName(), cargosPermitidos);
        cService.EditCourse(dto);

        return new ResponseEntity<String>(COURSE_EDITED, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCourse(Authentication authentication, @RequestParam int id)
            throws CourseDoesntExistException, AccessDeniedException, UserDoestExistException,
            DoesntExistUserTypeException {
        List<UserType> cargosPermitidos = Arrays
                .asList(new UserType[] { UserType.SECRETARY });
        vService.PermissionVerify(authentication.getName(), cargosPermitidos);
        cService.deleteCourse(id);

        return new ResponseEntity<String>(COURSE_DELETED, HttpStatus.OK);
    }
}
