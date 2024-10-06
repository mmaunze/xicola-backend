package mz.co.mefemasys.xicola.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import mz.co.mefemasys.xicola.backend.security.jwt.AuthEntryPointJwt;
import mz.co.mefemasys.xicola.backend.security.jwt.AuthTokenFilter;
import mz.co.mefemasys.xicola.backend.security.services.UtilizadorDetailsServiceImpl;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

  @Autowired
  UtilizadorDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
      return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    // Definindo rotas públicas
    String[] ROTAS_PUBLICAS = {
        "/autenticacao/**",
    };

    // Definindo rotas privadas (apenas requerem autenticação)
    String[] ROTAS_PRIVADAS = {
      "/estados/**", "/utilizadores/**", "/distritos/**", "/roles/**", "/provincias/**",

    };

    // Definindo rotas para roles específicas
    String[] ROTAS_ADMIN = {
        "/api/admin/**",  "/alunos/**",
    };

    String[] ROTAS_PROFESSOR = {
        "/api/professor/**", "/alunos/**",
    };

    String[] ROTAS_ALUNO = {
        "/api/aluno/**",
    };

    String[] ROTAS_FINANCEIRO = {
      "/api/admin/**",  "/alunos/**",
  };

  String[] ROTAS_PEDAGOGICO = {
      "/professores/**",  "/alunos/**",
  };

  String[] ROTAS_BIBLIOTECARIO = {
      "/api/aluno/**"
  };

    http.csrf(csrf -> csrf.disable())
        .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> 
          auth.requestMatchers(ROTAS_PUBLICAS).permitAll()
              .requestMatchers(ROTAS_PRIVADAS).authenticated()
              .requestMatchers(ROTAS_ADMIN).hasRole("ADMIN")
              .requestMatchers(ROTAS_PROFESSOR).hasRole("PROFESSOR")
              .requestMatchers(ROTAS_ALUNO).hasRole("ALUNO")
              .requestMatchers(ROTAS_FINANCEIRO).hasRole("FINANCEIRO")
              .requestMatchers(ROTAS_PEDAGOGICO).hasRole("PEDAGOGICO")
              .requestMatchers(ROTAS_BIBLIOTECARIO).hasRole("BIBLIOTECARIO") 
              .anyRequest().authenticated()
        );

    http.authenticationProvider(authenticationProvider());

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
