package com.lucassabit.projetomatricula.service.projects;

import java.util.List;

import com.lucassabit.projetomatricula.dto.client.project.WorkerEditDTO;
import com.lucassabit.projetomatricula.dto.send.WorkerSendDTO;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.login.StudentNotFoundException;
import com.lucassabit.projetomatricula.error.project.ProjectDoesntExistException;
import com.lucassabit.projetomatricula.error.project.WorkerDoesntExistException;

public interface WorkerService {
    public List<WorkerSendDTO> getAllWorkersFromAProject(int projectId) throws ProjectDoesntExistException;
    public WorkerSendDTO getWorker(String student_code) throws WorkerDoesntExistException, StudentNotFoundException;
    public void editWorker(WorkerEditDTO dto, String student_code, int project_id, String name) throws WorkerDoesntExistException, StudentNotFoundException, ProjectDoesntExistException, AccessDeniedException;
}
