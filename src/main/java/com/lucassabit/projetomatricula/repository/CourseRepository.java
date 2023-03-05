package com.lucassabit.projetomatricula.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucassabit.projetomatricula.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    public Optional<Course> findByName(String name);
    public boolean existsByName(String name);
}
