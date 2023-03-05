package com.lucassabit.projetomatricula.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lucassabit.projetomatricula.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    public Optional<Subject> findByRegisterCode(String registerCode);
}
