package com.lucassabit.projetomatricula.dto.send;

import java.util.List;

import com.lucassabit.projetomatricula.enumerators.WorkerLevel;

public class WorkerSendDTO {
    private Integer id;

    private String function;
    private WorkerLevel level;

    private List<ActivitiesSendDTO> activities;

    private ProjectSendDTO project;
    private StudentSendDTO student;

    public WorkerSendDTO() {
    }

    public WorkerSendDTO(Integer id, String function, WorkerLevel level, List<ActivitiesSendDTO> activities,
            ProjectSendDTO project, StudentSendDTO student) {
        this.id = id;
        this.function = function;
        this.level = level;
        this.activities = activities;
        this.project = project;
        this.student = student;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<ActivitiesSendDTO> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivitiesSendDTO> activities) {
        this.activities = activities;
    }

    public UserSendDTO getStudent() {
        return student;
    }

    public void setStudent(StudentSendDTO student) {
        this.student = student;
    }

    public ProjectSendDTO getProject() {
        return project;
    }

    public void setProject(ProjectSendDTO project) {
        this.project = project;
    }

}
