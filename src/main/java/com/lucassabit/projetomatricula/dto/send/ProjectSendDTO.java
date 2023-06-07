package com.lucassabit.projetomatricula.dto.send;

import java.util.List;

public class ProjectSendDTO {
    private Integer id;
    private String name;
    private String description;

    private TeacherSendDTO teacher;
    private List<UserWorkerSendDTO> workers;

    public ProjectSendDTO() {
    }

    public ProjectSendDTO(Integer id, String name, String description, TeacherSendDTO teacher,
            List<UserWorkerSendDTO> workers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.teacher = teacher;
        this.workers = workers;
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

    public TeacherSendDTO getTeacher() {
        return teacher;
    }

    public void setTeacher(TeacherSendDTO teacher) {
        this.teacher = teacher;
    }

    public List<UserWorkerSendDTO> getWorkers() {
        return workers;
    }

    public void setWorkers(List<UserWorkerSendDTO> workers) {
        this.workers = workers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
