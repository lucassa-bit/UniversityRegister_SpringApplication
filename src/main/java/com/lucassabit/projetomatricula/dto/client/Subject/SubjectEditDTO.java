package com.lucassabit.projetomatricula.dto.client.subject;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

public class SubjectEditDTO {
    private String name;
    private String teacher;

    @Pattern(regexp = "^(Segunda-feira|Terça-feira|Quarta-feira|Quinta-feira|Sexta-feira)$", message = "Erro na criação da disciplina: valor fora do padrão (dia da semana)")
    private String dayWeek1;
    @DateTimeFormat(pattern = "HH:mm")
    private String classTime1;

    @Pattern(regexp = "^(Segunda-feira|Terça-feira|Quarta-feira|Quinta-feira|Sexta-feira)$", message = "Erro na criação da disciplina: valor fora do padrão (dia da semana)")
    private String dayWeek2;
    @DateTimeFormat(pattern = "HH:mm")
    private String classTime2;

    public SubjectEditDTO() {
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getDayWeek1() {
        return dayWeek1;
    }

    public String getClassTime1() {
        return classTime1;
    }

    public String getClassTime2() {
        return classTime2;
    }

    public String getDayWeek2() {
        return dayWeek2;
    }
}
