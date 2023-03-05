package com.lucassabit.projetomatricula.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import com.lucassabit.projetomatricula.model.Student;

@Transactional
public interface StudentRepository extends UserRepository<Student> {
    public boolean existsByRegisterCode(String registerCode);
    public Optional<Student> findByRegisterCode(String registerCode);
}
