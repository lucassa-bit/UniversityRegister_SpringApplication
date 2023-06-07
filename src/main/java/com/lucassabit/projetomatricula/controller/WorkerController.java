package com.lucassabit.projetomatricula.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.lucassabit.projetomatricula.dto.client.project.WorkerEditDTO;
import com.lucassabit.projetomatricula.dto.send.WorkerSendDTO;
import com.lucassabit.projetomatricula.enumerators.UserType;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.login.DoesntExistUserTypeException;
import com.lucassabit.projetomatricula.error.login.StudentNotFoundException;
import com.lucassabit.projetomatricula.error.login.UserDoestExistException;
import com.lucassabit.projetomatricula.error.project.ProjectDoesntExistException;
import com.lucassabit.projetomatricula.error.project.WorkerDoesntExistException;
import com.lucassabit.projetomatricula.service.login.PermissionsVerifyService;
import com.lucassabit.projetomatricula.service.projects.WorkerService;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {
    @Autowired
    public WorkerService cService;
    @Autowired
    public PermissionsVerifyService vService;

    private final String WORKER_EDITED = "Edicao de curso realizado com sucesso!";

    @GetMapping("/project/{id}")
    public List<WorkerSendDTO> getAllStudents(@PathVariable int id) throws ProjectDoesntExistException {
        return cService.getAllWorkersFromAProject(id);
    }

    @GetMapping("/student/{register_code}")
    public WorkerSendDTO getStudent(@PathVariable String register_code)
            throws WorkerDoesntExistException, StudentNotFoundException {
        return cService.getWorker(register_code);
    }

    @PatchMapping("/project/{id}/student/{register_code}")
    public ResponseEntity<String> editWorker(Authentication authentication, @Valid @RequestBody WorkerEditDTO dto,
            @PathVariable String register_code, @PathVariable int id)
            throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException,
            WorkerDoesntExistException, StudentNotFoundException, ProjectDoesntExistException {
        List<UserType> cargosPermitidos = Arrays
                .asList(new UserType[] { UserType.TEACHER });
        vService.PermissionVerify(authentication.getName(), cargosPermitidos);
        cService.editWorker(dto, register_code, id, authentication.getName());

        return new ResponseEntity<String>(WORKER_EDITED, HttpStatus.OK);
    }
}
