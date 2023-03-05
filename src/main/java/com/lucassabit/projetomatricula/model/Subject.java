package com.lucassabit.projetomatricula.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.persistence.OneToMany;

import com.lucassabit.projetomatricula.dto.client.Subject.SubjectCreateDTO;
import com.lucassabit.projetomatricula.dto.client.Subject.SubjectEditDTO;
import com.lucassabit.projetomatricula.dto.send.SubjectSendDTO;
import com.lucassabit.projetomatricula.dto.send.SubjectStudentSendDTO;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String registerCode;

    @ManyToOne
    private Teacher teacher;

    @OneToMany(mappedBy = "subject")
    private List<Grade> students;

    @ManyToOne
    private Course course;
    private String dayWeek1;
    private LocalTime classTime1;
    private String dayWeek2;
    private LocalTime classTime2;

    public Subject() {
    }

    public Subject(String name, Teacher teacher, Course course, String dayWeek1, LocalTime classTime1,
            String dayWeek2, LocalTime classTime2) {
        this.name = name;
        this.teacher = teacher;
        this.course = course;
        this.classTime1 = classTime1;
        this.classTime2 = classTime2;
        this.dayWeek1 = dayWeek1;
        this.dayWeek2 = dayWeek2;
    }

    public static Subject fromCreateDto(SubjectCreateDTO dto, Teacher teacher, Course course) {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("HH:mm");
        return new Subject(dto.getName(), teacher, course, dto.getDayWeek1(),
                LocalTime.parse(dto.getClassTime1(), date), dto.getDayWeek2(),
                LocalTime.parse(dto.getClassTime2(), date));
    }

    public Subject fromEditDto(SubjectEditDTO dto, Teacher teacher) {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("HH:mm");

        this.name = dto.getName();
        if (teacher != null)
            this.teacher = teacher;

        this.dayWeek1 = dto.getDayWeek1();
        this.classTime1 = LocalTime.parse(dto.getClassTime1(), date);

        this.dayWeek2 = dto.getDayWeek2();
        this.classTime2 = LocalTime.parse(dto.getClassTime2(), date);

        return this;
    }

    public SubjectSendDTO toSendDTO() {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("HH:mm");
        return new SubjectSendDTO(id, name, this.teacher != null ? teacher.getName() : "",
                students.parallelStream().map((value) -> value.getStudent().toSendDTO())
                        .sorted((a, b) -> comparevalue(a.getId(), b.getId())).toList(),
                course.getName(),
                registerCode, dayWeek1,
                date.format(classTime1), dayWeek2, date.format(classTime2));
    }

    public SubjectStudentSendDTO toSubjectStudentDTO(Student student, Grade grade) {
        DateTimeFormatter date = DateTimeFormatter.ofPattern("HH:mm");
        return new SubjectStudentSendDTO(id, name, teacher != null ? teacher.getName() : "", student.toSendDTO(),
                course.getName(), registerCode, dayWeek1,
                date.format(classTime1), dayWeek2, date.format(classTime2), grade.getNota_parcial_1(),
                grade.getNota_parcial_2(),
                grade.getNota_final());
    }

    public Integer getId() {
        return id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Grade> getStudents() {
        return students;
    }

    public void setStudents(List<Grade> students) {
        this.students = students;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDayWeek1() {
        return dayWeek1;
    }

    public void setDayWeek1(String dayWeek1) {
        this.dayWeek1 = dayWeek1;
    }

    public String getDayWeek2() {
        return dayWeek2;
    }

    public void setDayWeek2(String dayWeek2) {
        this.dayWeek2 = dayWeek2;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public LocalTime getClassTime1() {
        return classTime1;
    }

    public void setClassTime1(LocalTime classTime1) {
        this.classTime1 = classTime1;
    }

    public LocalTime getClassTime2() {
        return classTime2;
    }

    public void setClassTime2(LocalTime classTime2) {
        this.classTime2 = classTime2;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((registerCode == null) ? 0 : registerCode.hashCode());
        result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
        result = prime * result + ((students == null) ? 0 : students.hashCode());
        result = prime * result + ((course == null) ? 0 : course.hashCode());
        result = prime * result + ((dayWeek1 == null) ? 0 : dayWeek1.hashCode());
        result = prime * result + ((classTime1 == null) ? 0 : classTime1.hashCode());
        result = prime * result + ((dayWeek2 == null) ? 0 : dayWeek2.hashCode());
        result = prime * result + ((classTime2 == null) ? 0 : classTime2.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Subject other = (Subject) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (registerCode == null) {
            if (other.registerCode != null)
                return false;
        } else if (!registerCode.equals(other.registerCode))
            return false;
        if (teacher == null) {
            if (other.teacher != null)
                return false;
        } else if (!teacher.equals(other.teacher))
            return false;
        if (students == null) {
            if (other.students != null)
                return false;
        } else if (!students.equals(other.students))
            return false;
        if (course == null) {
            if (other.course != null)
                return false;
        } else if (!course.equals(other.course))
            return false;
        if (dayWeek1 == null) {
            if (other.dayWeek1 != null)
                return false;
        } else if (!dayWeek1.equals(other.dayWeek1))
            return false;
        if (classTime1 == null) {
            if (other.classTime1 != null)
                return false;
        } else if (!classTime1.equals(other.classTime1))
            return false;
        if (dayWeek2 == null) {
            if (other.dayWeek2 != null)
                return false;
        } else if (!dayWeek2.equals(other.dayWeek2))
            return false;
        if (classTime2 == null) {
            if (other.classTime2 != null)
                return false;
        } else if (!classTime2.equals(other.classTime2))
            return false;
        return true;
    }

    private int comparevalue(Integer value1, Integer value2) {
        if (value1 > value2)
            return 1;
        else if (value1 < value2)
            return -1;
        else
            return 0;
    }

}