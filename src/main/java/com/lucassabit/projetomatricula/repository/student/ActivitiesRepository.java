package com.lucassabit.projetomatricula.repository.student;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucassabit.projetomatricula.model.Activity;

public interface ActivitiesRepository extends JpaRepository<Activity, Integer> {
    
}
