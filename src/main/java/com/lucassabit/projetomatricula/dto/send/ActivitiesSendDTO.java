package com.lucassabit.projetomatricula.dto.send;

import com.lucassabit.projetomatricula.enumerators.ActivityState;

public class ActivitiesSendDTO {
    private Integer id;
    private String description;
    private ActivityState state;

    public ActivitiesSendDTO(Integer id, String description, ActivityState state) {
        this.id = id;
        this.description = description;
        this.state = state;
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

    public ActivityState getState() {
        return state;
    }

    public void setState(ActivityState state) {
        this.state = state;
    }
}
