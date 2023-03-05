package com.lucassabit.projetomatricula.dto.send;

public class CourseSendDTO {
    private Integer id;
    private String name;

    public CourseSendDTO() {
    }

    public CourseSendDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
