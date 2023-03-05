package com.lucassabit.projetomatricula.model;

import java.util.List;

import com.lucassabit.projetomatricula.dto.client.Course.CourseCreateDTO;
import com.lucassabit.projetomatricula.dto.client.Course.CourseEditDTO;
import com.lucassabit.projetomatricula.dto.send.CourseSendDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "course")
    private List<Student> students;

    @OneToMany(mappedBy = "course")
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "course")
    private List<Subject> subjects;

    public Course() {
    }

    public Course(String name) {
        this.name = name;
    }

    public static Course fromCreateDto(CourseCreateDTO dto) {
        return new Course(dto.getName());
    }

    public Course fromEditDto(CourseEditDTO dto) {
        this.name = dto.getName();

        return this;
    }

    public CourseSendDTO toSendDTO() {
        return new CourseSendDTO(id, name);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

}
