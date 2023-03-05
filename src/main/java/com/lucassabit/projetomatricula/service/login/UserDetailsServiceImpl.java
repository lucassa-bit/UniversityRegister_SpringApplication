package com.lucassabit.projetomatricula.service.login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lucassabit.projetomatricula.data.UserDataDetails;
import com.lucassabit.projetomatricula.model.UserParent;
import com.lucassabit.projetomatricula.repository.GeneralUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private GeneralUserRepository secretaryRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<UserParent> user = secretaryRepository.findByLoginIgnoreCase(login);
        return new UserDataDetails(user);
    }
}
