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

import org.springframework.security.core.Authentication;

import com.lucassabit.projetomatricula.dto.client.project.ActivityCreateDTO;
import com.lucassabit.projetomatricula.dto.client.project.ActivityEditDTO;
import com.lucassabit.projetomatricula.dto.send.ActivitiesSendDTO;
import com.lucassabit.projetomatricula.enumerators.UserType;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.login.DoesntExistUserTypeException;
import com.lucassabit.projetomatricula.error.login.StudentNotFoundException;
import com.lucassabit.projetomatricula.error.login.UserDoestExistException;
import com.lucassabit.projetomatricula.error.project.ActivityDoesntExistException;
import com.lucassabit.projetomatricula.service.login.PermissionsVerifyService;
import com.lucassabit.projetomatricula.service.projects.ActivitiesService;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
        @Autowired
        public ActivitiesService cService;
        @Autowired
        public PermissionsVerifyService vService;

        private final String ACTIVITY_CREATED = "Cadastro do curso realizado com sucesso!";
        private final String ACTIVITY_EDITED = "Edicao de curso realizado com sucesso!";
        private final String ACTIVITY_DELETED = "Delete de curso realizado com sucesso!";

        @PostMapping("/student/{register_code}")
        @ResponseStatus(code = HttpStatus.CREATED)
        public ResponseEntity<String> createNewActivity(Authentication authentication,
                        @Valid @RequestBody ActivityCreateDTO dto,
                        @PathVariable String register_code)
                        throws StudentNotFoundException, UserDoestExistException,
                        AccessDeniedException, DoesntExistUserTypeException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.TEACHER });

                vService.PermissionVerify(authentication.getName(), cargosPermitidos);
                cService.createNewActivity(dto, register_code, authentication.getName());

                return new ResponseEntity<String>(ACTIVITY_CREATED, HttpStatus.CREATED);
        }

        @GetMapping("/student/{register_code}")
        public List<ActivitiesSendDTO> getAllActivitiesFromStudent(Authentication authentication,
                        @PathVariable String register_code)
                        throws UserDoestExistException, AccessDeniedException,
                        DoesntExistUserTypeException, StudentNotFoundException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.TEACHER });

                vService.PermissionVerify(authentication.getName(), cargosPermitidos);
                return cService.getWorkerActivities(register_code, authentication.getName());
        }

        @GetMapping("/student/{register_code}/activity/{id}")
        public ActivitiesSendDTO getAnActivity(Authentication authentication,
                        @PathVariable String register_code, @PathVariable int id)
                        throws UserDoestExistException, AccessDeniedException,
                        DoesntExistUserTypeException, StudentNotFoundException, ActivityDoesntExistException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.TEACHER });

                vService.PermissionVerify(authentication.getName(), cargosPermitidos);
                return cService.getWorkerActivity(register_code, id, authentication.getName());
        }

        @PatchMapping("/{id}")
        public ResponseEntity<String> editActivity(Authentication authentication,
                        @Valid @RequestBody ActivityEditDTO dto, @PathVariable int id)
                        throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException,
                        ActivityDoesntExistException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.TEACHER });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);
                cService.editActivity(dto, id, authentication.getName());

                return new ResponseEntity<String>(ACTIVITY_EDITED, HttpStatus.OK);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteActivity(Authentication authentication, @PathVariable int id)
                        throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException,
                        ActivityDoesntExistException {
                List<UserType> cargosPermitidos = Arrays
                                .asList(new UserType[] { UserType.TEACHER });
                vService.PermissionVerify(authentication.getName(), cargosPermitidos);
                cService.deleteActivity(id, authentication.getName());

                return new ResponseEntity<String>(ACTIVITY_DELETED, HttpStatus.OK);
        }
}
