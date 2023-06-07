package com.lucassabit.projetomatricula.dto.client.project;

import com.lucassabit.projetomatricula.enumerators.WorkerLevel;

public class WorkerEditDTO {
    private String function;
    private WorkerLevel level;

    public WorkerEditDTO() {
    }

    public String getFunction() {
        return function;
    }

    public WorkerLevel getLevel() {
        return level;
    }
}
