package com.xicola.xicola.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.Transient;

import com.xicola.xicola.model.Utilizador;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class MyUtilizadorDetails implements UserDetails {

    @Transient
    private transient Utilizador utilizador;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return utilizador.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return utilizador.getSenha();
    }

    @Override
    public String getUsername() {
        return utilizador.getUsername();
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
