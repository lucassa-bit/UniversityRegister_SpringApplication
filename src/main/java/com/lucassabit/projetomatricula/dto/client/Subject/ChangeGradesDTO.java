package com.lucassabit.projetomatricula.dto.client.subject;

public class ChangeGradesDTO {
    private String student_code;
    private Double grade_1;
    private Double grade_2;
    private Double final_grade;

    public ChangeGradesDTO() {
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

    public String getStudent_code() {
        return student_code;
    }

}
