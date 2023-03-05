package com.lucassabit.projetomatricula.dto.send;

import java.util.List;

public class SubjectSendDTO {
    private Integer id;
    private String name;
    private String teacher;
    private List<SubjectParticipantsSendDTO> students;
    private String course;
    private String registerCode;
    private String dayWeek1;
    private String classTime1;
    private String dayWeek2;
    private String classTime2;

    public SubjectSendDTO(Integer id, String name, String teacher, List<SubjectParticipantsSendDTO> students,
            String course, String registerCode, String dayWeek1, String classTime1, String dayWeek2,
            String classTime2) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.students = students;
        this.course = course;
        this.registerCode = registerCode;
        this.dayWeek1 = dayWeek1;
        this.classTime1 = classTime1;
        this.dayWeek2 = dayWeek2;
        this.classTime2 = classTime2;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public List<SubjectParticipantsSendDTO> getStudents() {
        return students;
    }

    public String getCourse() {
        return course;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public String getDayWeek1() {
        return dayWeek1;
    }

    public String getClassTime1() {
        return classTime1;
    }

    public String getDayWeek2() {
        return dayWeek2;
    }

    public String getClassTime2() {
        return classTime2;
    }
}
