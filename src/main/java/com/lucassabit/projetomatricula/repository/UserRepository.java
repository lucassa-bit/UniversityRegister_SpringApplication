package com.lucassabit.projetomatricula.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.lucassabit.projetomatricula.model.UserParent;

@NoRepositoryBean 
public interface UserRepository<T extends UserParent> extends JpaRepository<T, Integer> {
    public Optional<T> findByLoginIgnoreCase(String login);
    public Optional<T> findByName(String name);
    public boolean existsByLoginIgnoreCaseOrderByNameAsc(String login);
}
