package com.lucassabit.projetomatricula.service.projects;

import java.util.List;

import com.lucassabit.projetomatricula.dto.client.project.WorkerCreateDTO;
import com.lucassabit.projetomatricula.dto.client.project.ProjectCreateDTO;
import com.lucassabit.projetomatricula.dto.client.project.ProjectEditDTO;
import com.lucassabit.projetomatricula.dto.send.ProjectSendDTO;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.login.StudentNotFoundException;
import com.lucassabit.projetomatricula.error.login.TeacherNotFoundException;
import com.lucassabit.projetomatricula.error.project.AlreadyHaveProjectException;
import com.lucassabit.projetomatricula.error.project.ProjectDoesntExistException;

public interface ProjectService {
    public void createNewProject(ProjectCreateDTO dto, String teacher_code) throws TeacherNotFoundException, AlreadyHaveProjectException;

    public List<ProjectSendDTO> getAllProjects();

    public ProjectSendDTO getProjectById(int id) throws ProjectDoesntExistException;

    public void editProject(ProjectEditDTO dto, int project_id, String name) throws ProjectDoesntExistException, AccessDeniedException;

    public void deleteProject(int id, String name) throws ProjectDoesntExistException, AccessDeniedException;

    public void removeStudentFromProject(String student_registerCode, int projectId, String name)
            throws ProjectDoesntExistException, StudentNotFoundException, AccessDeniedException;

    public void addStudentFromProject(WorkerCreateDTO dto, int project_id, String student_code, String name)
            throws ProjectDoesntExistException, StudentNotFoundException, AccessDeniedException;
}
