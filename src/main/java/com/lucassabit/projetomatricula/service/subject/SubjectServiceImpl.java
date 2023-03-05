package com.lucassabit.projetomatricula.service.subject;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucassabit.projetomatricula.dto.client.Subject.ChangeGradesDTO;
import com.lucassabit.projetomatricula.dto.client.Subject.RegisterSubjectsDTO;
import com.lucassabit.projetomatricula.dto.client.Subject.SubjectCreateDTO;
import com.lucassabit.projetomatricula.dto.client.Subject.SubjectEditDTO;
import com.lucassabit.projetomatricula.dto.send.SubjectSendDTO;
import com.lucassabit.projetomatricula.dto.send.SubjectStudentSendDTO;
import com.lucassabit.projetomatricula.error.course.CourseDoesntExistException;
import com.lucassabit.projetomatricula.error.login.StudentNotFoundException;
import com.lucassabit.projetomatricula.error.login.TeacherNotFoundException;
import com.lucassabit.projetomatricula.error.subject.StudentIsNotRegisteredInSubjectException;
import com.lucassabit.projetomatricula.error.subject.SubjectDoesntExistException;
import com.lucassabit.projetomatricula.model.Course;
import com.lucassabit.projetomatricula.model.Grade;
import com.lucassabit.projetomatricula.model.Student;
import com.lucassabit.projetomatricula.model.Subject;
import com.lucassabit.projetomatricula.model.Teacher;
import com.lucassabit.projetomatricula.repository.CourseRepository;
import com.lucassabit.projetomatricula.repository.GradeRepository;
import com.lucassabit.projetomatricula.repository.StudentRepository;
import com.lucassabit.projetomatricula.repository.SubjectRepository;
import com.lucassabit.projetomatricula.repository.TeacherRepository;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository sRepository;
    @Autowired
    private GradeRepository gRepository;
    @Autowired
    private StudentRepository stRepository;
    @Autowired
    private TeacherRepository tRepository;
    @Autowired
    private CourseRepository cRepository;

    @Override
    public void createSubject(SubjectCreateDTO dto) throws TeacherNotFoundException, CourseDoesntExistException {
        Optional<Teacher> teacher = dto.getTeacher() != null && !dto.getTeacher().isEmpty()
                ? tRepository.findByRegisterCode(dto.getTeacher())
                : null;
        Optional<Course> course = cRepository.findByName(dto.getCourse());

        if (teacher != null && !teacher.isPresent())
            throw new TeacherNotFoundException(dto.getTeacher());
        if (!course.isPresent())
            throw new CourseDoesntExistException(dto.getCourse());

        Subject save = sRepository
                .save(Subject.fromCreateDto(dto, teacher != null ? teacher.get() : null, course.get()));
        save.setRegisterCode(
                repeatValue(course.get().getId(), 3)
                        + course.get().getId()
                        + repeatValue(save.getId(), 3)
                        + save.getId());
        sRepository.save(save);

        if (teacher != null) {
            teacher.get().getSubjects().add(save);
            tRepository.save(teacher.get());
        }

        course.get().getSubjects().add(save);
        cRepository.save(course.get());
    }

    @Override
    public List<SubjectSendDTO> getAllSubjects(String course) {
        Optional<Course> courseSearch = cRepository.findByName(course);
        return courseSearch.get().getSubjects().parallelStream().map((value) -> value.toSendDTO()).toList();
    }

    @Override
    public List<SubjectStudentSendDTO> getSubjectFromStudent(String registrationCodeStudent)
            throws StudentNotFoundException {
        Optional<Student> student = stRepository.findByRegisterCode(registrationCodeStudent);

        if (!student.isPresent())
            throw new StudentNotFoundException("com código de registro " + registrationCodeStudent);

        return student.get().getSubjects().parallelStream().map(
                (value) -> value.getSubject().toSubjectStudentDTO(student.get(), value)).toList();
    }

    @Override
    public List<SubjectSendDTO> getSubjectFromTeacher(String registrationCodeTeacher)
            throws TeacherNotFoundException {
        Optional<Teacher> teacher = tRepository.findByRegisterCode(registrationCodeTeacher);

        if (!teacher.isPresent())
            throw new TeacherNotFoundException("com código de registro " + registrationCodeTeacher);

        return teacher.get().getSubjects().parallelStream().map((value) -> value.toSendDTO()).toList();
    }

    @Override
    public void editSubject(SubjectEditDTO dto)
            throws SubjectDoesntExistException, CourseDoesntExistException, TeacherNotFoundException {
        System.out.println(dto.getId());
        Optional<Subject> subject = sRepository.findById(dto.getId());
        if (!subject.isPresent())
            throw new SubjectDoesntExistException(dto.getName());

        Optional<Teacher> teacher = dto.getTeacher().isEmpty()
                ? null
                : subject.get().getTeacher() != null && subject.get().getTeacher().getName().equals(dto.getTeacher())
                        ? null
                        : tRepository.findByRegisterCode(dto.getTeacher());

        if (teacher != null && !teacher.isPresent()) {
            throw new TeacherNotFoundException(dto.getTeacher());
        } else if (teacher != null && teacher.isPresent()) {
            Teacher save = subject.get().getTeacher();
            if (save != null) {
                save.getSubjects().remove(subject.get());
                tRepository.save(save);
            }

            teacher.get().getSubjects().add(subject.get());
            tRepository.save(teacher.get());
        }

        sRepository.save(subject.get()
                .fromEditDto(
                        dto,
                        teacher != null ? teacher.get() : null));
    }

    @Override
    public void deleteSubject(int id) throws SubjectDoesntExistException {
        Optional<Subject> subject = sRepository.findById(id);

        if (!subject.isPresent())
            throw new SubjectDoesntExistException("Desconhecido");

        if (subject.get().getTeacher() != null) {
            subject.get().getTeacher().getSubjects().remove(subject.get());
        }

        subject.get().getCourse().getSubjects().remove(subject.get());

        for (Grade grade : subject.get().getStudents()) {
            Student student = grade.getStudent();
            student.getSubjects().remove(grade);
            stRepository.save(student);

            gRepository.delete(grade);
        }

        sRepository.delete(subject.get());
    }

    @Override
    public void readjusmentRegister(RegisterSubjectsDTO dto)
            throws StudentNotFoundException {
        Optional<Student> studentOptional = stRepository.findByRegisterCode(dto.getRegisterCodeStudent());

        if (!studentOptional.isPresent())
            throw new StudentNotFoundException("com código de registro " + dto.getRegisterCodeStudent());

        Student student = studentOptional.get();

        List<Grade> subjectsToRemove = student.getSubjects().parallelStream().filter((value) -> {
            return !dto.getRegisterCodeSubject().contains(value.getSubject().getRegisterCode());
        }).toList();

        for (Grade grade : subjectsToRemove) {
            Subject subject = grade.getSubject();

            student.getSubjects().remove(grade);
            subject.getStudents().remove(grade);

            sRepository.save(subject);
            gRepository.delete(grade);
        }

        for (String code : dto.getRegisterCodeSubject()) {
            if (student.getSubjects().parallelStream()
                    .noneMatch((value) -> value.getSubject().getRegisterCode().equals(code))) {
                Optional<Subject> subject = sRepository.findByRegisterCode(code);

                if (subject.isPresent()) {
                    Grade grade = new Grade(subject.get(), student);
                    subject.get().getStudents().add(grade);
                    student.getSubjects().add(grade);

                    gRepository.save(grade);
                    sRepository.save(subject.get());
                }
            }
        }
        stRepository.save(student);
    }

    @Override
    public void changeGrades(ChangeGradesDTO dto)
            throws StudentNotFoundException, StudentIsNotRegisteredInSubjectException {
        Optional<Student> student = stRepository.findByRegisterCode(dto.getRegisterCodeStudent());

        if (!student.isPresent())
            throw new StudentNotFoundException("com código de registro " + dto.getRegisterCodeStudent());

        List<Grade> gradeList = student.get().getSubjects().parallelStream()
                .filter((value) -> value.getSubject().getRegisterCode().equals(dto.getRegisterCodeSubject())).toList();

        if (gradeList.isEmpty())
            throw new StudentIsNotRegisteredInSubjectException(
                    "com código de registro " + dto.getRegisterCodeStudent());

        Grade grade = gradeList.get(0).fromGradeDTO(dto);
        gRepository.save(grade);
    }

    public String repeatValue(int id, int repeat) {
        return "0".repeat(repeat - (id + "").length());
    }
}
