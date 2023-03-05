package com.lucassabit.projetomatricula.service.subject;

import java.util.List;

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

public interface SubjectService {
        public void createSubject(SubjectCreateDTO dto) throws TeacherNotFoundException, CourseDoesntExistException;

        public List<SubjectSendDTO> getAllSubjects(String course);

        public void editSubject(SubjectEditDTO dto)
                        throws SubjectDoesntExistException, CourseDoesntExistException, TeacherNotFoundException;

        public void deleteSubject(int id) throws SubjectDoesntExistException;

        public void readjusmentRegister(RegisterSubjectsDTO dto)
                        throws StudentNotFoundException;

        public List<SubjectStudentSendDTO> getSubjectFromStudent(String registrationCodeStudent)
                        throws StudentNotFoundException;

        public List<SubjectSendDTO> getSubjectFromTeacher(String registrationCodeTeacher) throws TeacherNotFoundException;

        public void changeGrades(ChangeGradesDTO dto)
                        throws StudentNotFoundException, StudentIsNotRegisteredInSubjectException;
}
