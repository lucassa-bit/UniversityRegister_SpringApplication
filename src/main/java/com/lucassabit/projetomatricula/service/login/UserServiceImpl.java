package com.lucassabit.projetomatricula.service.login;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lucassabit.projetomatricula.dto.client.SubjectParticipants.SubjectParticipantsCreateDTO;
import com.lucassabit.projetomatricula.dto.client.SubjectParticipants.SubjectParticipantsEditDTO;
import com.lucassabit.projetomatricula.dto.client.User.UserCreateDTO;
import com.lucassabit.projetomatricula.dto.client.User.UserEditDTO;
import com.lucassabit.projetomatricula.dto.send.SubjectParticipantsSendDTO;
import com.lucassabit.projetomatricula.dto.send.UserSendDTO;
import com.lucassabit.projetomatricula.enumerators.UserType;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.course.CourseDoesntExistException;
import com.lucassabit.projetomatricula.error.login.DoesntExistUserTypeException;
import com.lucassabit.projetomatricula.error.login.EncodingPasswordException;
import com.lucassabit.projetomatricula.error.login.LoginAlreadyExistsException;
import com.lucassabit.projetomatricula.error.login.UserDoestExistException;
import com.lucassabit.projetomatricula.model.Course;
import com.lucassabit.projetomatricula.model.Grade;
import com.lucassabit.projetomatricula.model.Student;
import com.lucassabit.projetomatricula.model.Subject;
import com.lucassabit.projetomatricula.model.Teacher;
import com.lucassabit.projetomatricula.model.UserParent;
import com.lucassabit.projetomatricula.model.Secretary;
import com.lucassabit.projetomatricula.repository.CourseRepository;
import com.lucassabit.projetomatricula.repository.GeneralUserRepository;
import com.lucassabit.projetomatricula.repository.GradeRepository;
import com.lucassabit.projetomatricula.repository.StudentRepository;
import com.lucassabit.projetomatricula.repository.SubjectRepository;
import com.lucassabit.projetomatricula.repository.TeacherRepository;
import com.lucassabit.projetomatricula.repository.SecretaryRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private GeneralUserRepository gRepository;
    @Autowired
    private GradeRepository grRepository;
    @Autowired
    private SecretaryRepository scRepository;
    @Autowired
    private SubjectRepository sbRepository;
    @Autowired
    private TeacherRepository tRepository;
    @Autowired
    private StudentRepository sRepository;
    @Autowired
    private CourseRepository cRepository;
    @Autowired
    private PasswordEncoder pEncoder;

    @Override
    public void createNewUsuario(UserCreateDTO dto) throws LoginAlreadyExistsException, EncodingPasswordException {
        if (gRepository.existsByLoginIgnoreCaseOrderByNameAsc(dto.getLogin())) {
            throw new LoginAlreadyExistsException(dto.getLogin());
        }

        scRepository.save(Secretary.fromCreateDto(dto, pEncoder));
    }

    @Override
    public void createNewUsuario(SubjectParticipantsCreateDTO dto, UserType userType)
            throws LoginAlreadyExistsException, EncodingPasswordException, CourseDoesntExistException,
            DoesntExistUserTypeException {
        Optional<Course> course = cRepository.findByName(dto.getCourse());

        if (!course.isPresent())
            throw new CourseDoesntExistException(dto.getCourse());
        if (gRepository.existsByLoginIgnoreCaseOrderByNameAsc(dto.getLogin())) {
            throw new LoginAlreadyExistsException(dto.getLogin());
        }
        LocalDate today = LocalDate.now();
        int id = course.get().getId();

        String registerCode = (today.get(ChronoField.YEAR) + "").substring(2)
                + (today.get(ChronoField.MONTH_OF_YEAR) <= 6 ? "1" : "2")
                + ("0".repeat(3 - ((id) + "").length()) + id);

        switch (userType) {
            case STUDENT:
                Student saveStudent = sRepository.save(Student.fromCreateDto(dto, course.get(), pEncoder));
                int idStudent = saveStudent.getId();
                saveStudent.setRegistration(registerCode + formatId(idStudent));
                saveStudent = sRepository.save(saveStudent);
                course.get().getStudents().add(saveStudent);
                cRepository.save(course.get());
                break;
            case TEACHER:
                Teacher saveTeacher = tRepository.save(Teacher.fromCreateDto(dto, course.get(), pEncoder));
                int idTeacher = saveTeacher.getId();
                saveTeacher.setRegistration(registerCode + formatId(idTeacher));
                tRepository.save(saveTeacher);
                course.get().getTeachers().add(saveTeacher);
                cRepository.save(course.get());
                break;
            default:
                throw new DoesntExistUserTypeException();
        }
    }

    @Override
    public List<UserSendDTO> pickAllUsers() {
        return scRepository.findAll().parallelStream().map((value) -> value.toSendDTO()).toList();
    }

    @Override
    public List<SubjectParticipantsSendDTO> pickAllUsers(String course, UserType userT)
            throws CourseDoesntExistException {

        return userT.equals(UserType.STUDENT)
                ? sRepository.findAll().parallelStream().filter((value) -> value.getCourse().getName().equals(course))
                        .map((value) -> value.toSendDTO()).sorted((a, b) -> compareName(a.getName(), b.getName()))
                        .toList()
                : tRepository.findAll().parallelStream().filter((value) -> value.getCourse().getName().equals(course))
                        .map((value) -> value.toSendDTO()).sorted((a, b) -> compareName(a.getName(), b.getName()))
                        .toList();
    }

    @Override
    public void editarUsuario(UserEditDTO dto)
            throws UserDoestExistException, LoginAlreadyExistsException, AccessDeniedException {
        if (dto.getId() == 1)
            throw new AccessDeniedException();

        Optional<Secretary> user = scRepository.findById(dto.getId());

        if (!user.isPresent())
            throw new UserDoestExistException(dto.getName());
        if (dto.getlogin() != null && gRepository.existsByLoginIgnoreCaseOrderByNameAsc(dto.getlogin()))
            throw new LoginAlreadyExistsException(dto.getlogin());

        scRepository.save(user.get().fromEditDto(dto, pEncoder));
    }

    @Override
    public void editarUsuario(SubjectParticipantsEditDTO dto, UserType userType)
            throws LoginAlreadyExistsException, EncodingPasswordException, CourseDoesntExistException,
            UserDoestExistException, DoesntExistUserTypeException {
        Optional<Course> course = cRepository.findByName(dto.getCourse());

        if (dto.getCourse() != null && !course.isPresent())
            throw new CourseDoesntExistException(dto.getName());

        switch (userType) {
            case STUDENT: {
                Optional<Student> user = sRepository.findById(dto.getId());

                if (!user.isPresent())
                    throw new UserDoestExistException(dto.getName());
                if (!user.get().getLogin().equals(dto.getlogin())
                        && gRepository.existsByLoginIgnoreCaseOrderByNameAsc(dto.getlogin()))
                    throw new LoginAlreadyExistsException(dto.getlogin());

                if (user.get().getCourse().getId() != course.get().getId()) {
                    user.get().getCourse().getStudents().remove(user.get());

                    Student save = sRepository.save(user.get().fromEditDto(dto, course.get(), pEncoder));
                    course.get().getStudents().add(save);

                    for (Grade grade : save.getSubjects()) {
                        save.getSubjects().remove(grade);

                        Subject subject = grade.getSubject();
                        subject.getStudents().remove(grade);

                        sbRepository.save(subject);
                        grRepository.delete(grade);
                    }

                    cRepository.save(course.get());
                    sRepository.save(save);
                } else {
                    sRepository.save(user.get().fromEditDto(dto, course.get(), pEncoder));
                }
                break;
            }
            case TEACHER: {
                Optional<Teacher> user = tRepository.findById(dto.getId());

                if (!user.isPresent())
                    throw new UserDoestExistException(dto.getName());
                if (!user.get().getLogin().equals(dto.getlogin())
                        && gRepository.existsByLoginIgnoreCaseOrderByNameAsc(dto.getlogin()))
                    throw new LoginAlreadyExistsException(dto.getlogin());
                if (user.get().getCourse().getId() != course.get().getId()) {
                    user.get().getCourse().getTeachers().remove(user.get());

                    Teacher save = tRepository.save(user.get().fromEditDto(dto, course.get(), pEncoder));
                    course.get().getTeachers().add(save);
                    cRepository.save(course.get());

                    for (Subject subject : save.getSubjects()) {
                        subject.setTeacher(null);
                        save.getSubjects().remove(subject);

                        sbRepository.save(subject);
                    }

                    cRepository.save(course.get());
                    tRepository.save(save);
                } else {
                    tRepository.save(user.get().fromEditDto(dto, course.get(), pEncoder));
                }
                break;
            }
            default:
                throw new DoesntExistUserTypeException();

        }
    }

    @Override
    public UserSendDTO getUsuarioByLogin(String login)
            throws UserDoestExistException, DoesntExistUserTypeException {
        Optional<UserParent> user = gRepository.findByLoginIgnoreCase(login);
        if (!user.isPresent())
            throw new UserDoestExistException(login);
        switch (user.get().getUserType()) {
            case STUDENT: {
                return sRepository.findByLoginIgnoreCase(login).get().toSendDTO();
            }
            case TEACHER: {
                return tRepository.findByLoginIgnoreCase(login).get().toSendDTO();
            }
            case SECRETARY: {
                return user.get().toSendDTO();
            }
            default:
                throw new DoesntExistUserTypeException();
        }
    }

    @Override
    public void deleteUsuario(int id)
            throws AccessDeniedException, DoesntExistUserTypeException, UserDoestExistException {
        if (id == 1)
            throw new AccessDeniedException();

        Optional<UserParent> user = gRepository.findById(id);

        if (!user.isPresent())
            throw new UserDoestExistException("usuario com o id " + id);

        switch (user.get().getUserType()) {
            case STUDENT: {
                Optional<Student> userStudent = sRepository.findById(id);

                Course course = userStudent.get().getCourse();
                course.getStudents().remove(user.get());

                for (Grade grade : userStudent.get().getSubjects()) {
                    Subject subject = grade.getSubject();
                    subject.getStudents().remove(grade);

                    sbRepository.save(subject);
                    grRepository.delete(grade);
                }

                cRepository.save(course);
                sRepository.delete(userStudent.get());

            }
            case TEACHER: {
                Optional<Teacher> userTeacher = tRepository.findById(id);
                Course course = user.get().getCourse();
                course.getTeachers().remove(user.get());
                cRepository.save(course);

                for (Subject subject : userTeacher.get().getSubjects()) {
                    subject.setTeacher(null);
                    sbRepository.save(subject);
                }

                cRepository.save(course);
                tRepository.delete(userTeacher.get());
            }
            case SECRETARY: {
                gRepository.delete(user.get());
                break;
            }
            default:
                throw new DoesntExistUserTypeException();
        }

    }

    @Override
    public UserSendDTO getUsuarioById(Integer id, UserType userT)
            throws UserDoestExistException, DoesntExistUserTypeException {
        if (userT.equals(UserType.SECRETARY)) {
            Optional<Secretary> user = scRepository.findById(id);

            if (user.isPresent()) {
                return user.get().toSendDTO();
            }
        }

        Optional<Teacher> user = tRepository.findById(id);

        if (user.isPresent()) {
            return user.get().toSendDTO();
        }

        Optional<Student> userStudent = sRepository.findById(id);

        if (userStudent.isPresent())
            return userStudent.get().toSendDTO();

        throw new UserDoestExistException(id + "");
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

    private String formatId(int id) {
        return "0".repeat(6 - ("" + id).length()) + id;
    }
}
