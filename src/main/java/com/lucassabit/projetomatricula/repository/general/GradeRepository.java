package com.lucassabit.projetomatricula.repository.general;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucassabit.projetomatricula.model.Grade;

public interface GradeRepository extends JpaRepository<Grade, Integer> {

}
