package com.lucassabit.projetomatricula.service.projects;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucassabit.projetomatricula.dto.client.project.WorkerEditDTO;
import com.lucassabit.projetomatricula.dto.send.WorkerSendDTO;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.login.StudentNotFoundException;
import com.lucassabit.projetomatricula.error.project.ProjectDoesntExistException;
import com.lucassabit.projetomatricula.error.project.WorkerDoesntExistException;
import com.lucassabit.projetomatricula.model.Project;
import com.lucassabit.projetomatricula.model.Student;
import com.lucassabit.projetomatricula.model.Worker;
import com.lucassabit.projetomatricula.repository.general.ProjectRepository;
import com.lucassabit.projetomatricula.repository.student.StudentRepository;
import com.lucassabit.projetomatricula.repository.student.WorkerRepository;

@Service
public class WorkerServiceImpl implements WorkerService {
    @Autowired
    private ProjectRepository pRepository;
    @Autowired
    private WorkerRepository wRepository;
    @Autowired
    private StudentRepository sRepository;

    @Override
    public List<WorkerSendDTO> getAllWorkersFromAProject(int projectId) throws ProjectDoesntExistException {
        Optional<Project> project = pRepository.findById(projectId);

        if (!project.isPresent())
            throw new ProjectDoesntExistException(projectId);

        return project.get().getWorkers().stream().map(value -> value.toSendDTO()).toList();
    }

    @Override
    public WorkerSendDTO getWorker(String student_code)
            throws WorkerDoesntExistException, StudentNotFoundException {
        Optional<Student> student = sRepository.findByRegisterCode(student_code);

        if (!student.isPresent())
            throw new StudentNotFoundException(student_code);

        Worker worker = student.get().getWorker();
        if (worker == null)
            throw new WorkerDoesntExistException();

        return worker.toSendDTO();
    }

    @Override
    public void editWorker(WorkerEditDTO dto, String student_code, int project_id, String name)
            throws WorkerDoesntExistException, StudentNotFoundException, ProjectDoesntExistException, AccessDeniedException {
        Optional<Student> student = sRepository.findByRegisterCode(student_code);

        if (!student.isPresent())
            throw new StudentNotFoundException(student_code);
        if (!student.get().getWorker().getProject().getTeacher().getLogin().equals(name))
            throw new AccessDeniedException("professor " + name + "não pode mexer no aluno");

        Worker worker = student.get().getWorker();

        if (worker == null)
            throw new WorkerDoesntExistException();

        Optional<Project> projects = pRepository.findById(project_id);

        if (!projects.isPresent())
            throw new ProjectDoesntExistException(project_id);

        worker.fromEditDto(dto, projects.isPresent() ? projects.get() : null);
        wRepository.save(worker);
    }

}
