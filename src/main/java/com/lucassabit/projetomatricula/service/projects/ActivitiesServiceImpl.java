package com.lucassabit.projetomatricula.service.projects;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucassabit.projetomatricula.dto.client.project.ActivityCreateDTO;
import com.lucassabit.projetomatricula.dto.client.project.ActivityEditDTO;
import com.lucassabit.projetomatricula.dto.send.ActivitiesSendDTO;
import com.lucassabit.projetomatricula.enumerators.ActivityState;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.login.StudentNotFoundException;
import com.lucassabit.projetomatricula.error.project.ActivityDoesntExistException;
import com.lucassabit.projetomatricula.model.Activity;
import com.lucassabit.projetomatricula.model.Student;
import com.lucassabit.projetomatricula.repository.student.ActivitiesRepository;
import com.lucassabit.projetomatricula.repository.student.StudentRepository;
import com.lucassabit.projetomatricula.repository.student.WorkerRepository;

@Service
public class ActivitiesServiceImpl implements ActivitiesService {
    @Autowired
    private ActivitiesRepository aRepository;
    @Autowired
    private StudentRepository sRepository;
    @Autowired
    private WorkerRepository wRepository;

    @Override
    public void createNewActivity(ActivityCreateDTO dto, String student_code, String name)
            throws StudentNotFoundException, AccessDeniedException {
        Optional<Student> worker = sRepository.findByRegisterCode(student_code);

        if (!worker.isPresent())
            throw new StudentNotFoundException(student_code);
        if (!worker.get().getWorker().getProject().getTeacher().getLogin().equals(name))
            throw new AccessDeniedException("professor " + name + "não pode mexer no projeto");

        aRepository.save(Activity.fromCreateDto(dto, worker.get().getWorker()));
    }

    @Override
    public List<ActivitiesSendDTO> getWorkerActivities(String student_registerCode, String name)
            throws StudentNotFoundException, AccessDeniedException {
        Optional<Student> worker = sRepository.findByRegisterCode(student_registerCode);

        if (!worker.isPresent())
            throw new StudentNotFoundException(student_registerCode);
        if (!worker.get().getWorker().getProject().getTeacher().getLogin().equals(name))
            throw new AccessDeniedException("professor " + name + "não pode mexer no projeto");

        return worker.get().getWorker().getActivities().stream().map(value -> value.toSendDTO()).toList();
    }

    @Override
    public ActivitiesSendDTO getWorkerActivity(String student_registerCode, int activityId, String name)
            throws StudentNotFoundException, ActivityDoesntExistException, AccessDeniedException {
        Optional<Student> worker = sRepository.findByRegisterCode(student_registerCode);

        if (!worker.isPresent())
            throw new StudentNotFoundException(student_registerCode);
        if (!worker.get().getWorker().getProject().getTeacher().getLogin().equals(name))
            throw new AccessDeniedException("professor " + name + "não pode mexer no projeto");

        Optional<Activity> activity = worker.get().getWorker().getActivities().stream()
                .filter(value -> value.getId() == activityId).findFirst();
        if (!activity.isPresent())
            throw new ActivityDoesntExistException(activityId);

        return activity.get().toSendDTO();
    }

    @Override
    public void editActivity(ActivityEditDTO dto, int activity_id, String name)
            throws ActivityDoesntExistException, AccessDeniedException {
        Optional<Activity> activities = aRepository.findById(activity_id);

        if (!activities.isPresent())
            throw new ActivityDoesntExistException(activity_id);
        if (!activities.get().getWorker().getProject().getTeacher().getLogin().equals(name))
            throw new AccessDeniedException("professor " + name + "não pode mexer no projeto");

        aRepository.save(activities.get().fromEditDto(dto));
    }

    @Override
    public void deleteActivity(int id, String name) throws ActivityDoesntExistException, AccessDeniedException {
        Optional<Activity> activities = aRepository.findById(id);

        if (!activities.isPresent())
            throw new ActivityDoesntExistException(id);
        if (!activities.get().getWorker().getProject().getTeacher().getLogin().equals(name))
            throw new AccessDeniedException("professor " + name + "não pode mexer no projeto");
        if (activities.get().getState().equals(ActivityState.FINISHED))
            throw new AccessDeniedException("Não é possível apagar uma atividade finalizada");

        activities.get().getWorker().getActivities().remove(activities.get());

        wRepository.save(activities.get().getWorker());
        aRepository.delete(activities.get());
    }

}
