package com.lucassabit.projetomatricula.repository.general;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucassabit.projetomatricula.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    
}
