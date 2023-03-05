package com.lucassabit.projetomatricula.dto.send;

public class SubjectStudentSendDTO {
    private Integer id;

    private String name;
    private String teacher;
    private String course;
    private String registerCode;
    private String dayWeek1;
    private String classTime1;
    private String dayWeek2;
    private String classTime2;

    private SubjectParticipantsSendDTO student;

    private Double grade_1;
    private Double grade_2;
    private Double final_grade;

    public SubjectStudentSendDTO(Integer id, String name, String teacher, SubjectParticipantsSendDTO student,
            String course, String registerCode, String dayWeek1, String classTime1, String dayWeek2, String classTime2,
            Double grade_1, Double grade_2, Double final_grade) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.student = student;
        this.course = course;
        this.registerCode = registerCode;
        this.dayWeek1 = dayWeek1;
        this.classTime1 = classTime1;
        this.dayWeek2 = dayWeek2;
        this.classTime2 = classTime2;
        this.grade_1 = grade_1;
        this.grade_2 = grade_2;
        this.final_grade = final_grade;
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

    public SubjectParticipantsSendDTO getStudent() {
        return student;
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

    public Double getGrade_1() {
        return grade_1;
    }

    public Double getGrade_2() {
        return grade_2;
    }

    public Double getFinal_grade() {
        return final_grade;
    }

}
