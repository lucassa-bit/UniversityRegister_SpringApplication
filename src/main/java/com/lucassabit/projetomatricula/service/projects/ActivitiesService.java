package com.lucassabit.projetomatricula.service.projects;

import java.util.List;

import com.lucassabit.projetomatricula.dto.client.project.ActivityCreateDTO;
import com.lucassabit.projetomatricula.dto.client.project.ActivityEditDTO;
import com.lucassabit.projetomatricula.dto.send.ActivitiesSendDTO;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.login.StudentNotFoundException;
import com.lucassabit.projetomatricula.error.project.ActivityDoesntExistException;

public interface ActivitiesService {
    public void createNewActivity(ActivityCreateDTO dto, String student_code, String name) throws StudentNotFoundException, AccessDeniedException;
    public List<ActivitiesSendDTO> getWorkerActivities(String student_registerCode, String name) throws StudentNotFoundException, AccessDeniedException;
    public ActivitiesSendDTO getWorkerActivity(String student_registerCode, int activityId, String name) throws StudentNotFoundException, ActivityDoesntExistException, AccessDeniedException;
    public void editActivity(ActivityEditDTO dto, int activity_id, String name) throws ActivityDoesntExistException, AccessDeniedException;
    public void deleteActivity(int id, String name) throws ActivityDoesntExistException, AccessDeniedException;
}
