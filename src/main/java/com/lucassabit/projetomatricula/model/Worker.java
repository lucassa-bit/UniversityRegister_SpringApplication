package com.lucassabit.projetomatricula.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.lucassabit.projetomatricula.dto.client.project.WorkerCreateDTO;
import com.lucassabit.projetomatricula.dto.client.project.WorkerEditDTO;
import com.lucassabit.projetomatricula.dto.send.UserWorkerSendDTO;
import com.lucassabit.projetomatricula.dto.send.WorkerSendDTO;
import com.lucassabit.projetomatricula.enumerators.WorkerLevel;

@Entity
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String function;
    private WorkerLevel level;

    @OneToMany(mappedBy = "worker")
    private List<Activity> activities;

    @ManyToOne
    private Project project;

    @OneToOne(mappedBy = "worker")
    private Student student;

    public Worker() {

    }

    public Worker(Student student, Project project, String function, WorkerLevel level) {
        this.student = student;
        this.function = function;
        this.level = level;
        this.project = project;
    }

    public static Worker fromCreateDto(WorkerCreateDTO dto, Student student, Project project) {
        return new Worker(student, project, dto.getStudent_function(), dto.getStudent_level());
    }

    public Worker fromEditDto(WorkerEditDTO dto, Project project) {
        if (dto.getFunction() != null)
            this.function = dto.getFunction();
        if (dto.getLevel() != null)
            this.level = dto.getLevel();
        if (project != null)
            this.project = project;

        return this;
    }

    public WorkerSendDTO toSendDTO() {
        return new WorkerSendDTO(id, function, level, activities.stream().map(value -> value.toSendDTO()).toList(),
                project.toSendDTO(), student.toSendDTO());
    }

    public UserWorkerSendDTO withoutProjecttoSendDTO() {
        return new UserWorkerSendDTO(id, function, level, activities.stream().map(value -> value.toSendDTO()).toList(),
                project.withoutProjectToSendDTO(), student.toSendDTO());
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public WorkerLevel getLevel() {
        return level;
    }

    public void setLevel(WorkerLevel level) {
        this.level = level;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
