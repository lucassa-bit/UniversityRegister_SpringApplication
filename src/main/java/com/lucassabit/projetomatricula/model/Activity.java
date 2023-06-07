package com.lucassabit.projetomatricula.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.lucassabit.projetomatricula.dto.client.project.ActivityCreateDTO;
import com.lucassabit.projetomatricula.dto.client.project.ActivityEditDTO;
import com.lucassabit.projetomatricula.dto.send.ActivitiesSendDTO;
import com.lucassabit.projetomatricula.enumerators.ActivityState;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;
    private ActivityState state;

    @ManyToOne
    private Worker worker;

    public Activity() {
    }

    public Activity(String activityDescription, ActivityState state, Worker worker) {
        this.description = activityDescription;
        this.state = state;
        this.worker = worker;
    }

    public static Activity fromCreateDto(ActivityCreateDTO dto, Worker worker) {
        return new Activity(dto.getDescription(), ActivityState.ATTRIBUTED, worker);
    }

    public Activity fromEditDto(ActivityEditDTO dto) {
        if (dto.getDescription() != null)
            this.description = dto.getDescription();
        if (dto.getState() != null)
            this.state = dto.getState();

        return this;
    }

    public ActivitiesSendDTO toSendDTO() {
        return new ActivitiesSendDTO(id, description, state);
    }

    public ActivityState getState() {
        return state;
    }

    public void setState(ActivityState state) {
        this.state = state;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
