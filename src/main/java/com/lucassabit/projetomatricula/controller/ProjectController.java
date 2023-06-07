package com.lucassabit.projetomatricula.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lucassabit.projetomatricula.dto.client.project.WorkerCreateDTO;
import com.lucassabit.projetomatricula.dto.client.project.ProjectCreateDTO;
import com.lucassabit.projetomatricula.dto.client.project.ProjectEditDTO;
import com.lucassabit.projetomatricula.dto.send.ProjectSendDTO;
import com.lucassabit.projetomatricula.enumerators.UserType;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.login.DoesntExistUserTypeException;
import com.lucassabit.projetomatricula.error.login.StudentNotFoundException;
import com.lucassabit.projetomatricula.error.login.TeacherNotFoundException;
import com.lucassabit.projetomatricula.error.login.UserDoestExistException;
import com.lucassabit.projetomatricula.error.project.AlreadyHaveProjectException;
import com.lucassabit.projetomatricula.error.project.ProjectDoesntExistException;
import com.lucassabit.projetomatricula.service.login.PermissionsVerifyService;
import com.lucassabit.projetomatricula.service.projects.ProjectService;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    public ProjectService cService;
    @Autowired
    public PermissionsVerifyService vService;

    private final String PROJECT_CREATED = "Cadastro do curso realizado com sucesso!";
    private final String PROJECT_EDITED = "Edicao de curso realizado com sucesso!";
    private final String PROJECT_DELETED = "Delete de curso realizado com sucesso!";

    @PostMapping("/teacher/{register_code}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> createNewProject(Authentication authentication,
            @Valid @RequestBody ProjectCreateDTO dto, @PathVariable String register_code)
            throws UserDoestExistException, AccessDeniedException,
            DoesntExistUserTypeException, TeacherNotFoundException, AlreadyHaveProjectException {
        List<UserType> cargosPermitidos = Arrays
                .asList(new UserType[] { UserType.TEACHER });

        vService.PermissionVerify(authentication.getName(), cargosPermitidos);
        cService.createNewProject(dto, register_code);

        return new ResponseEntity<String>(PROJECT_CREATED, HttpStatus.CREATED);
    }

    @GetMapping
    public List<ProjectSendDTO> getAllProjects() {
        return cService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ProjectSendDTO getProject(@PathVariable int id) throws ProjectDoesntExistException {
        return cService.getProjectById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> editProject(Authentication authentication, @Valid @RequestBody ProjectEditDTO dto,
            @PathVariable int id)
            throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException,
            ProjectDoesntExistException {
        List<UserType> cargosPermitidos = Arrays
                .asList(new UserType[] { UserType.TEACHER });
        vService.PermissionVerify(authentication.getName(), cargosPermitidos);
        System.out.println(authentication.getName());
        cService.editProject(dto, id, authentication.getName());

        return new ResponseEntity<String>(PROJECT_EDITED, HttpStatus.OK);
    }

    @PatchMapping("/add/{id}/student/{register_code}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<String> AddingStudent(Authentication authentication, @Valid @RequestBody WorkerCreateDTO dto,
            @PathVariable int id, @PathVariable String register_code)
            throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException,
            ProjectDoesntExistException, StudentNotFoundException {
        List<UserType> cargosPermitidos = Arrays
                .asList(new UserType[] { UserType.TEACHER });

        vService.PermissionVerify(authentication.getName(), cargosPermitidos);
        cService.addStudentFromProject(dto, id, register_code, authentication.getName());

        return new ResponseEntity<String>(PROJECT_CREATED, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/remove/{id}/student/{register_code}")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<String> removeStudent(Authentication authentication, @PathVariable int id,
            @PathVariable String register_code)
            throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException,
            ProjectDoesntExistException, StudentNotFoundException {
        List<UserType> cargosPermitidos = Arrays
                .asList(new UserType[] { UserType.TEACHER });

        vService.PermissionVerify(authentication.getName(), cargosPermitidos);
        cService.removeStudentFromProject(register_code, id, authentication.getName());

        return new ResponseEntity<String>(PROJECT_CREATED, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(Authentication authentication, @PathVariable int id)
            throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException,
            ProjectDoesntExistException {
        List<UserType> cargosPermitidos = Arrays
                .asList(new UserType[] { UserType.TEACHER });
        vService.PermissionVerify(authentication.getName(), cargosPermitidos);
        cService.deleteProject(id, authentication.getName());

        return new ResponseEntity<String>(PROJECT_DELETED, HttpStatus.OK);
    }
}
