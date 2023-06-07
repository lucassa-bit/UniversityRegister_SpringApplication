package com.lucassabit.projetomatricula.repository.general.user;

import javax.transaction.Transactional;

import com.lucassabit.projetomatricula.model.UserParent;

@Transactional
public interface GeneralUserRepository extends UserRepository<UserParent> {
}
