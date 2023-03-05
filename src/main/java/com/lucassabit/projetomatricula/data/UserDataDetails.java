package com.lucassabit.projetomatricula.data;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lucassabit.projetomatricula.model.UserParent;

public class UserDataDetails implements UserDetails {

    private Optional<UserParent> usuario;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDataDetails() {
    }

    public UserDataDetails(Optional<UserParent> usuario) {
        this();
        this.usuario = usuario;
    }

    public UserParent getUsuario() {
        if(!usuario.isPresent()) return null;
        return usuario.get();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return usuario.orElse(new UserParent()).getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.orElse(new UserParent()).getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
