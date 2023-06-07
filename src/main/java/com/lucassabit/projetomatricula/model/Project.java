package com.lucassabit.projetomatricula.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.lucassabit.projetomatricula.dto.client.project.ProjectCreateDTO;
import com.lucassabit.projetomatricula.dto.client.project.ProjectEditDTO;
import com.lucassabit.projetomatricula.dto.send.ProjectSendDTO;
import com.lucassabit.projetomatricula.dto.send.SimpleProjectSendDTO;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;

    @OneToOne(mappedBy = "project")
    private Teacher teacher;

    @OneToMany(mappedBy = "project")
    private List<Worker> workers;

    public Project() {
    }

    public Project(String name, String description, Teacher teacher) {
        this.name = name;
        this.description = description;
        this.teacher = teacher;
    }

    public static Project fromCreateDto(ProjectCreateDTO dto, Teacher teacher) {
        return new Project(dto.getName(), dto.getDescription(), teacher);
    }

    public Project fromEditDto(ProjectEditDTO dto) {
        if (dto.getName() != null)
            this.name = dto.getName();
        if (dto.getDescription() != null)
            this.description = dto.getDescription();

        return this;
    }

    public ProjectSendDTO toSendDTO() {
        return new ProjectSendDTO(id, name, description, teacher.toSendDTO(),
                workers.stream().map(value -> value.withoutProjecttoSendDTO()).toList());
    }

    public SimpleProjectSendDTO withoutProjectToSendDTO() {
        return new SimpleProjectSendDTO(name, description);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public Integer getId() {
        return id;
    }
}
