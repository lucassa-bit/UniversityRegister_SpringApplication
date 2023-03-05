package com.lucassabit.projetomatricula.service.course;

import java.util.List;

import com.lucassabit.projetomatricula.dto.client.Course.CourseCreateDTO;
import com.lucassabit.projetomatricula.dto.client.Course.CourseEditDTO;
import com.lucassabit.projetomatricula.dto.send.CourseSendDTO;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.course.CourseAlreadyExistsException;
import com.lucassabit.projetomatricula.error.course.CourseDoesntExistException;

public interface CourseService {
    public void CreateNewCourse(CourseCreateDTO dto) throws CourseAlreadyExistsException;
    public List<CourseSendDTO> getAllCourses();
    public void EditCourse(CourseEditDTO dto) throws CourseAlreadyExistsException, CourseDoesntExistException;
    public void deleteCourse(int id) throws CourseDoesntExistException, AccessDeniedException;
}
