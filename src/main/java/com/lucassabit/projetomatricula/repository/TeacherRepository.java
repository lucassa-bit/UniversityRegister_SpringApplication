package com.lucassabit.projetomatricula.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import com.lucassabit.projetomatricula.model.Teacher;

@Transactional
public interface TeacherRepository extends UserRepository<Teacher> {
    public boolean existsByRegisterCode(String registerCode);

    public Optional<Teacher> findByRegisterCode(String registerCode);

}
