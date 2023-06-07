package com.lucassabit.projetomatricula.service.projects;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucassabit.projetomatricula.dto.client.project.WorkerCreateDTO;
import com.lucassabit.projetomatricula.dto.client.project.ProjectCreateDTO;
import com.lucassabit.projetomatricula.dto.client.project.ProjectEditDTO;
import com.lucassabit.projetomatricula.dto.send.ProjectSendDTO;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.login.StudentNotFoundException;
import com.lucassabit.projetomatricula.error.login.TeacherNotFoundException;
import com.lucassabit.projetomatricula.error.project.AlreadyHaveProjectException;
import com.lucassabit.projetomatricula.error.project.ProjectDoesntExistException;
import com.lucassabit.projetomatricula.model.Project;
import com.lucassabit.projetomatricula.model.Student;
import com.lucassabit.projetomatricula.model.Teacher;
import com.lucassabit.projetomatricula.model.Worker;
import com.lucassabit.projetomatricula.repository.general.ProjectRepository;
import com.lucassabit.projetomatricula.repository.general.user.TeacherRepository;
import com.lucassabit.projetomatricula.repository.student.StudentRepository;
import com.lucassabit.projetomatricula.repository.student.WorkerRepository;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepository pRepository;
    @Autowired
    private TeacherRepository tRepository;
    @Autowired
    private StudentRepository sRepository;
    @Autowired
    private WorkerRepository wRepository;

    @Override
    public void createNewProject(ProjectCreateDTO dto, String teacher_code)
            throws TeacherNotFoundException, AlreadyHaveProjectException {
        Optional<Teacher> teacher = tRepository.findByRegisterCode(teacher_code);

        if (!teacher.isPresent())
            throw new TeacherNotFoundException(teacher_code);
        if (teacher.get().getProject() != null)
            throw new AlreadyHaveProjectException(teacher.get());

        teacher.get().setProject(pRepository.save(Project.fromCreateDto(dto, teacher.get())));
        tRepository.save(teacher.get());
    }

    @Override
    public List<ProjectSendDTO> getAllProjects() {
        return pRepository.findAll().stream().map(value -> {
            return value.toSendDTO();
        }).toList();
    }

    @Override
    public ProjectSendDTO getProjectById(int id) throws ProjectDoesntExistException {
        Optional<Project> projects = pRepository.findById(id);

        if (!projects.isPresent())
            throw new ProjectDoesntExistException(id);

        return projects.get().toSendDTO();
    }

    @Override
    public void editProject(ProjectEditDTO dto, int project_id, String name)
            throws ProjectDoesntExistException, AccessDeniedException {
        Optional<Project> project = pRepository.findById(project_id);

        System.out.println(project.get().getTeacher().getLogin().equals(name));

        if (!project.isPresent())
            throw new ProjectDoesntExistException(project_id);
        if (!project.get().getTeacher().getLogin().equals(name))
            throw new AccessDeniedException("professor " + name + "não pode mexer no projeto");

        pRepository.save(project.get().fromEditDto(dto));
    }

    @Override
    public void deleteProject(int id, String name) throws ProjectDoesntExistException, AccessDeniedException {
        Optional<Project> project = pRepository.findById(id);

        if (!project.isPresent())
            throw new ProjectDoesntExistException(id);
        if (!project.get().getTeacher().getLogin().equals(name))
            throw new AccessDeniedException("professor " + name + "não pode mexer no projeto");

        for (Worker worker : project.get().getWorkers()) {
            Student student = worker.getStudent();
            student.setWorker(null);

            sRepository.save(student);
            wRepository.delete(worker);
        }
        Teacher teacher = project.get().getTeacher();
        teacher.setProject(null);

        tRepository.save(teacher);
        pRepository.delete(project.get());
    }

    @Override
    public void removeStudentFromProject(String student_registerCode, int projectId, String name)
            throws ProjectDoesntExistException, StudentNotFoundException, AccessDeniedException {
        Optional<Project> project = pRepository.findById(projectId);

        if (!project.isPresent())
            throw new ProjectDoesntExistException(projectId);
        if (!project.get().getTeacher().getLogin().equals(name))
            throw new AccessDeniedException("professor " + name + "não pode mexer no projeto");

        Worker student = project.get().getWorkers().stream()
                .filter(value -> value.getStudent().getRegisterCode().equals(student_registerCode)).findAny().get();

        student.setStudent(null);
        project.get().getWorkers().remove(student);

        sRepository.save(student.getStudent());
        pRepository.save(project.get());
        wRepository.delete(student);
    }

    @Override
    public void addStudentFromProject(WorkerCreateDTO dto, int project_id, String student_code, String name)
            throws ProjectDoesntExistException, StudentNotFoundException, AccessDeniedException {
        Optional<Project> project = pRepository.findById(project_id);
        
        if (!project.isPresent())
            throw new ProjectDoesntExistException(project_id);
        if (!project.get().getTeacher().getLogin().equals(name))
            throw new AccessDeniedException("professor " + name + "não pode mexer no projeto");

        Optional<Student> student = sRepository.findByRegisterCode(student_code);

        if (!student.isPresent())
            throw new StudentNotFoundException(student_code);

        Worker worker = new Worker(student.get(), project.get(), dto.getStudent_function(), dto.getStudent_level());

        student.get().setWorker(worker);
        project.get().getWorkers().add(worker);

        wRepository.save(worker);
        sRepository.save(student.get());
        pRepository.save(project.get());
    }
}
