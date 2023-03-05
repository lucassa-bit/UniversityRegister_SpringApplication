package com.lucassabit.projetomatricula.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.lucassabit.projetomatricula.dto.client.Subject.ChangeGradesDTO;

@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Student student;

    private double nota_parcial_1;
    private double nota_parcial_2;
    private double nota_final;

    public Grade() {
    }

    public Grade(Subject subject, Student student) {
        this.subject = subject;
        this.student = student;
        this.nota_parcial_1 = 0;
        this.nota_parcial_2 = 0;
        this.nota_final = 0;
    }

    public Grade fromGradeDTO(ChangeGradesDTO dto) {
        this.nota_parcial_1 = dto.getGrade_1() != null ? dto.getGrade_2() : 0;
        this.nota_parcial_2 = dto.getGrade_2() != null ? dto.getGrade_2() : 0;
        this.nota_final = dto.getFinal_grade() != null ? dto.getFinal_grade() : 0;

        return this;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public double getNota_parcial_1() {
        return nota_parcial_1;
    }

    public void setNota_parcial_1(double nota_parcial_1) {
        this.nota_parcial_1 = nota_parcial_1;
    }

    public double getNota_parcial_2() {
        return nota_parcial_2;
    }

    public void setNota_parcial_2(double nota_parcial_2) {
        this.nota_parcial_2 = nota_parcial_2;
    }

    public double getNota_final() {
        return nota_final;
    }

    public void setNota_final(double nota_final) {
        this.nota_final = nota_final;
    }

    public Integer getId() {
        return id;
    }
}
