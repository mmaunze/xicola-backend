package com.xicola.xicola.security;

import com.xicola.xicola.model.Utilizador;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.xicola.xicola.repository.UtilizadorRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UtilizadorRepository utilizadorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilizador utilizador = utilizadorRepository.getUserByUsername(username);

        if (utilizador == null) {
            throw new UsernameNotFoundException("Utilizador não encontrado");
        }

        return new MyUtilizadorDetails(utilizador);
    }
}
