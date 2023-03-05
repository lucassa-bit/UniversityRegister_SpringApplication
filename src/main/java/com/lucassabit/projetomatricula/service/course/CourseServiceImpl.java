package com.lucassabit.projetomatricula.service.course;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucassabit.projetomatricula.dto.client.Course.CourseCreateDTO;
import com.lucassabit.projetomatricula.dto.client.Course.CourseEditDTO;
import com.lucassabit.projetomatricula.dto.send.CourseSendDTO;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.course.CourseAlreadyExistsException;
import com.lucassabit.projetomatricula.error.course.CourseDoesntExistException;
import com.lucassabit.projetomatricula.model.Course;
import com.lucassabit.projetomatricula.model.Student;
import com.lucassabit.projetomatricula.model.Subject;
import com.lucassabit.projetomatricula.model.Teacher;
import com.lucassabit.projetomatricula.repository.CourseRepository;
import com.lucassabit.projetomatricula.repository.StudentRepository;
import com.lucassabit.projetomatricula.repository.SubjectRepository;
import com.lucassabit.projetomatricula.repository.TeacherRepository;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository cRepository;
    @Autowired
    private TeacherRepository tRepository;
    @Autowired
    private StudentRepository sRepository;
    @Autowired
    private SubjectRepository sbRepository;

    @Override
    public void CreateNewCourse(CourseCreateDTO dto) throws CourseAlreadyExistsException {
        if (cRepository.existsByName(dto.getName()))
            throw new CourseAlreadyExistsException(dto.getName());

        cRepository.save(Course.fromCreateDto(dto));
    }

    @Override
    public List<CourseSendDTO> getAllCourses() {
        return cRepository.findAll().parallelStream().map((value) -> value.toSendDTO())
                .sorted((a, b) -> compareName(a.getName(), b.getName())).toList();
    }

    @Override
    public void EditCourse(CourseEditDTO dto) throws CourseAlreadyExistsException, CourseDoesntExistException {
        Optional<Course> value = cRepository.findById(dto.getId());

        if (!value.isPresent())
            throw new CourseDoesntExistException(dto.getName());
        if (cRepository.existsByName(dto.getName()))
            throw new CourseAlreadyExistsException(dto.getName());

        cRepository.save(value.get().fromEditDto(dto));
    }

    @Override
    public void deleteCourse(int id) throws CourseDoesntExistException, AccessDeniedException {
        if (id == 1)
            throw new AccessDeniedException();

        Optional<Course> course = cRepository.findById(id);
        Course courseChanger = cRepository.getById(1);

        if (!course.isPresent())
            throw new CourseDoesntExistException("desconhecido");

        for (Student student : course.get().getStudents()) {
            student.setCourse(courseChanger);
            Student save = sRepository.save(student);

            courseChanger.getStudents().add(save);
            cRepository.save(courseChanger);
        }

        for (Teacher teacher : course.get().getTeachers()) {
            teacher.setCourse(courseChanger);
            Teacher save = tRepository.save(teacher);

            courseChanger.getTeachers().add(save);
            cRepository.save(courseChanger);
        }

        for (Subject subject : course.get().getSubjects()) {
            subject.setCourse(courseChanger);
            Subject save = sbRepository.save(subject);

            courseChanger.getSubjects().add(save);
            cRepository.save(courseChanger);
        }

        cRepository.delete(course.get());
    }

    private int compareName(String name1, String name2) {
        for (int index = 0; index < name1.length() && index < name2.length(); index++) {
            if (name1.charAt(index) > name2.charAt(index))
                return 1;
            else if (name1.charAt(index) < name2.charAt(index))
                return -1;
        }
        return 0;
    }

}
