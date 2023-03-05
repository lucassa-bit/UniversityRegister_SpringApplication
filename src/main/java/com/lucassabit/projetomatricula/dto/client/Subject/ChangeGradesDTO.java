package com.lucassabit.projetomatricula.dto.client.Subject;

import javax.validation.constraints.NotBlank;

public class ChangeGradesDTO {
    @NotBlank(message = "Erro na atualização das notas: valor em branco/nulo (matricula do estudante)")
    private String registerCodeStudent;
    @NotBlank(message = "Erro na atualização das notas: valor em branco/nulo (matricula da disciplina)")
    private String registerCodeSubject;
    private Double grade_1;
    private Double grade_2;
    private Double final_grade;

    public ChangeGradesDTO() {

    }

    public String getRegisterCodeStudent() {
        return registerCodeStudent;
    }

    public String getRegisterCodeSubject() {
        return registerCodeSubject;
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
