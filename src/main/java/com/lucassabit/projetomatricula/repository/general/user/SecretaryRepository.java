package com.lucassabit.projetomatricula.repository.general.user;

import javax.transaction.Transactional;

import com.lucassabit.projetomatricula.model.Secretary;

@Transactional
public interface SecretaryRepository extends UserRepository<Secretary> {
    
}
