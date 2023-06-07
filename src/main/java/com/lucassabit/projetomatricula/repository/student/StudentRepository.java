package com.lucassabit.projetomatricula.repository.student;

import java.util.Optional;

import javax.transaction.Transactional;

import com.lucassabit.projetomatricula.model.Student;
import com.lucassabit.projetomatricula.repository.general.user.UserRepository;

@Transactional
public interface StudentRepository extends UserRepository<Student> {
    public boolean existsByRegisterCode(String registerCode);
    public Optional<Student> findByRegisterCode(String registerCode);
}
