package com.lucassabit.projetomatricula.service.subject;

import java.util.List;

import com.lucassabit.projetomatricula.dto.client.subject.ChangeGradesDTO;
import com.lucassabit.projetomatricula.dto.client.subject.RegisterSubjectsDTO;
import com.lucassabit.projetomatricula.dto.client.subject.SubjectCreateDTO;
import com.lucassabit.projetomatricula.dto.client.subject.SubjectEditDTO;
import com.lucassabit.projetomatricula.dto.send.SubjectSendDTO;
import com.lucassabit.projetomatricula.dto.send.SubjectStudentSendDTO;
import com.lucassabit.projetomatricula.error.course.CourseDoesntExistException;
import com.lucassabit.projetomatricula.error.login.StudentNotFoundException;
import com.lucassabit.projetomatricula.error.login.TeacherNotFoundException;
import com.lucassabit.projetomatricula.error.subject.StudentIsNotRegisteredInSubjectException;
import com.lucassabit.projetomatricula.error.subject.SubjectDoesntExistException;

public interface SubjectService {
        public void createSubject(SubjectCreateDTO dto, String teacher_code, String course_name)
                        throws TeacherNotFoundException, CourseDoesntExistException;

        public List<SubjectSendDTO> getAllSubjects(String course);

        public void editSubject(SubjectEditDTO dto, int subject_id)
                        throws SubjectDoesntExistException, CourseDoesntExistException, TeacherNotFoundException;

        public void deleteSubject(int id) throws SubjectDoesntExistException;

        public void readjusmentRegister(RegisterSubjectsDTO dto, String student_code)
                        throws StudentNotFoundException;

        public List<SubjectStudentSendDTO> getSubjectFromStudent(String registrationCodeStudent)
                        throws StudentNotFoundException;

        public List<SubjectSendDTO> getSubjectFromTeacher(String registrationCodeTeacher)
                        throws TeacherNotFoundException;

        public void changeGrades(ChangeGradesDTO dto, String student_code)
                        throws StudentNotFoundException, StudentIsNotRegisteredInSubjectException;
}
