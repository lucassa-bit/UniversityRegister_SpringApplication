package com.lucassabit.projetomatricula.service.login;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lucassabit.projetomatricula.dto.client.user.StudentCreateDTO;
import com.lucassabit.projetomatricula.dto.client.user.StudentEditDTO;
import com.lucassabit.projetomatricula.dto.client.user.TeacherCreateDTO;
import com.lucassabit.projetomatricula.dto.client.user.TeacherEditDTO;
import com.lucassabit.projetomatricula.dto.client.user.UserCreateDTO;
import com.lucassabit.projetomatricula.dto.client.user.UserEditDTO;
import com.lucassabit.projetomatricula.dto.send.StudentSendDTO;
import com.lucassabit.projetomatricula.dto.send.TeacherSendDTO;
import com.lucassabit.projetomatricula.dto.send.UserSendDTO;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.course.CourseDoesntExistException;
import com.lucassabit.projetomatricula.error.login.DoesntExistUserTypeException;
import com.lucassabit.projetomatricula.error.login.EncodingPasswordException;
import com.lucassabit.projetomatricula.error.login.LoginAlreadyExistsException;
import com.lucassabit.projetomatricula.error.login.UserDoestExistException;
import com.lucassabit.projetomatricula.model.Course;
import com.lucassabit.projetomatricula.model.Grade;
import com.lucassabit.projetomatricula.model.Project;
import com.lucassabit.projetomatricula.model.Student;
import com.lucassabit.projetomatricula.model.Subject;
import com.lucassabit.projetomatricula.model.Teacher;
import com.lucassabit.projetomatricula.model.UserParent;
import com.lucassabit.projetomatricula.model.Worker;
import com.lucassabit.projetomatricula.model.Secretary;
import com.lucassabit.projetomatricula.repository.general.CourseRepository;
import com.lucassabit.projetomatricula.repository.general.GradeRepository;
import com.lucassabit.projetomatricula.repository.general.ProjectRepository;
import com.lucassabit.projetomatricula.repository.general.SubjectRepository;
import com.lucassabit.projetomatricula.repository.general.user.GeneralUserRepository;
import com.lucassabit.projetomatricula.repository.general.user.SecretaryRepository;
import com.lucassabit.projetomatricula.repository.general.user.TeacherRepository;
import com.lucassabit.projetomatricula.repository.student.StudentRepository;
import com.lucassabit.projetomatricula.repository.student.WorkerRepository;

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
    private ProjectRepository pRepository;
    @Autowired
    private WorkerRepository wRepository;
    @Autowired
    private PasswordEncoder pEncoder;

    @Override
    public void createNewSecretary(UserCreateDTO dto) throws LoginAlreadyExistsException, EncodingPasswordException {
        if (gRepository.existsByLoginIgnoreCaseOrderByNameAsc(dto.getLogin())) {
            throw new LoginAlreadyExistsException(dto.getLogin());
        }

        scRepository.save(Secretary.fromCreateDto(dto, pEncoder));
    }

    @Override
    public void createNewStudent(StudentCreateDTO dto)
            throws EncodingPasswordException, LoginAlreadyExistsException, CourseDoesntExistException {
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
                + ("2" + "0".repeat(2 - ((id) + "").length()) + id);

        Student saveStudent = sRepository.save(Student.fromCreateDto(dto, course.get(), pEncoder));
        int idStudent = saveStudent.getId();
        saveStudent.setRegistration(registerCode + formatId(idStudent));
        saveStudent = sRepository.save(saveStudent);
        course.get().getStudents().add(saveStudent);
        cRepository.save(course.get());
    }

    @Override
    public void createNewTeacher(TeacherCreateDTO dto)
            throws CourseDoesntExistException, LoginAlreadyExistsException, EncodingPasswordException {
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
                + ("1" + "0".repeat(2 - ((id) + "").length()) + id);

        Teacher saveTeacher = tRepository.save(Teacher.fromCreateDto(dto, course.get(), pEncoder));
        int idTeacher = saveTeacher.getId();

        saveTeacher.setRegistration(registerCode + formatId(idTeacher));
        course.get().getTeachers().add(saveTeacher);

        tRepository.save(saveTeacher);
        cRepository.save(course.get());
    }

    @Override
    public List<UserSendDTO> pickAllSecretaries() {
        return scRepository.findAll().parallelStream().map((value) -> value.toSendDTO()).toList();
    }

    @Override
    public List<StudentSendDTO> pickAllStudents(String course) {
        return sRepository.findAll().parallelStream().filter((value) -> value.getCourse().getName().equals(course))
                .map((value) -> value.toSendDTO()).sorted((a, b) -> compareName(a.getName(), b.getName()))
                .toList();
    }

    @Override
    public List<TeacherSendDTO> pickAllTeachers(String course) {
        return tRepository.findAll().parallelStream().filter((value) -> value.getCourse().getName().equals(course))
                .map((value) -> value.toSendDTO()).sorted((a, b) -> compareName(a.getName(), b.getName()))
                .toList();
    }

    @Override
    public UserSendDTO getSecretaryById(Integer id) throws UserDoestExistException {
        Optional<Secretary> user = scRepository.findById(id);

        if (!user.isPresent())
            throw new UserDoestExistException(id + "");

        return user.get().toSendDTO();
    }

    @Override
    public StudentSendDTO getStudentById(Integer id) throws UserDoestExistException {
        Optional<Student> userStudent = sRepository.findById(id);

        if (userStudent.isPresent())
            throw new UserDoestExistException(id + "");

        return userStudent.get().toSendDTO();
    }

    @Override
    public TeacherSendDTO getTeacherById(Integer id) throws UserDoestExistException {
        Optional<Teacher> user = tRepository.findById(id);

        if (user.isPresent())
            throw new UserDoestExistException(id + "");

        return user.get().toSendDTO();
    }

    @Override
    public UserSendDTO getUsuarioByLogin(String login) throws UserDoestExistException, DoesntExistUserTypeException {
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
    public void editSecretary(UserEditDTO dto, int id_user)
            throws AccessDeniedException, UserDoestExistException, LoginAlreadyExistsException {
        if (id_user == 1)
            throw new AccessDeniedException();

        Optional<Secretary> user = scRepository.findById(id_user);

        if (!user.isPresent())
            throw new UserDoestExistException(dto.getName());
        if (dto.getlogin() != null && gRepository.existsByLoginIgnoreCaseOrderByNameAsc(dto.getlogin()))
            throw new LoginAlreadyExistsException(dto.getlogin());

        scRepository.save(user.get().fromEditDto(dto, pEncoder));
    }

    @Override
    public void editStudent(StudentEditDTO dto, int id_user)
            throws LoginAlreadyExistsException, UserDoestExistException, CourseDoesntExistException {
        Optional<Course> course = Optional.empty();

        if (dto.getCourse() != null) {
            course = cRepository.findByName(dto.getCourse());

            if (dto.getCourse() != null && !course.isPresent())
                throw new CourseDoesntExistException(dto.getName());
        }

        Optional<Student> user = sRepository.findById(id_user);

        if (!user.isPresent())
            throw new UserDoestExistException(dto.getName());
        if (dto.getlogin() != null
                && !user.get().getLogin().equals(dto.getlogin())
                && gRepository.existsByLoginIgnoreCaseOrderByNameAsc(dto.getlogin()))
            throw new LoginAlreadyExistsException(dto.getlogin());

        if (course.isPresent()
                && user.get().getCourse().getId() != course.get().getId()) {
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
            sRepository.save(user.get().fromEditDto(dto, null, pEncoder));
        }
    }

    @Override
    public void editTeacher(TeacherEditDTO dto, int id_user)
            throws CourseDoesntExistException, UserDoestExistException, LoginAlreadyExistsException {
        Optional<Course> course = Optional.empty();

        if (dto.getCourse() != null) {
            course = cRepository.findByName(dto.getCourse());

            if (dto.getCourse() != null && !course.isPresent())
                throw new CourseDoesntExistException(dto.getName());
        }

        Optional<Teacher> user = tRepository.findById(id_user);

        if (!user.isPresent())
            throw new UserDoestExistException(dto.getName());
        if (dto.getlogin() != null
                && !user.get().getLogin().equals(dto.getlogin())
                && gRepository.existsByLoginIgnoreCaseOrderByNameAsc(dto.getlogin()))
            throw new LoginAlreadyExistsException(dto.getlogin());

        if (course.isPresent()
                && user.get().getCourse().getId() != course.get().getId()) {
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
            tRepository.save(user.get().fromEditDto(dto, null, pEncoder));
        }
    }

    @Override
    public void deleteSecretary(int id) throws UserDoestExistException {
        Optional<UserParent> user = gRepository.findById(id);

        if (!user.isPresent())
            throw new UserDoestExistException("usuario com o id " + id);

        gRepository.delete(user.get());

    }

    @Override
    public void deleteTeacher(int id) throws UserDoestExistException {
        Optional<Teacher> userTeacher = tRepository.findById(id);

        if (!userTeacher.isPresent())
            throw new UserDoestExistException("usuario com o id " + id);

        Course course = userTeacher.get().getCourse();
        course.getTeachers().remove(userTeacher.get());
        cRepository.save(course);

        for (Subject subject : userTeacher.get().getSubjects()) {
            subject.setTeacher(null);
            sbRepository.save(subject);
        }

        Project project = userTeacher.get().getProject();
        project.setTeacher(null);

        pRepository.save(project);
        cRepository.save(course);
        tRepository.delete(userTeacher.get());
    }

    @Override
    public void deleteStudent(int id) throws UserDoestExistException {
        Optional<Student> userStudent = sRepository.findById(id);

        if (!userStudent.isPresent())
            throw new UserDoestExistException("usuario com o id " + id);

        Course course = userStudent.get().getCourse();
        course.getStudents().remove(userStudent.get());

        for (Grade grade : userStudent.get().getSubjects()) {
            Subject subject = grade.getSubject();
            subject.getStudents().remove(grade);

            sbRepository.save(subject);
            grRepository.delete(grade);
        }

        Worker worker = userStudent.get().getWorker();
        Project project = worker.getProject();
        project.getWorkers().remove(worker);

        cRepository.save(course);
        pRepository.save(project);

        wRepository.delete(worker);
        sRepository.delete(userStudent.get());
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
