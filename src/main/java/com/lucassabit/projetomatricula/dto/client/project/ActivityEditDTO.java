package com.lucassabit.projetomatricula.dto.client.project;

import com.lucassabit.projetomatricula.enumerators.ActivityState;

public class ActivityEditDTO {
    private String description;
    private ActivityState state;

    public ActivityEditDTO() {
    }

    public String getDescription() {
        return description;
    }

    public ActivityState getState() {
        return state;
    }
}
