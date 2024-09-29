package mz.co.mefemasys.xicola.backend.security.services;

import mz.co.mefemasys.xicola.backend.models.Utilizador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mz.co.mefemasys.xicola.backend.repository.UtilizadorRepository;

@Service
public class UtilizadorDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UtilizadorRepository utilizadorRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Utilizador utilizador = utilizadorRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Utilizador inexistente: " + username));

    return UtilizadorDetailsImpl.build(utilizador);
  }

}
