package mz.co.mefemasys.xicola_backend.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import mz.co.mefemasys.xicola_backend.security.UserDetailsServiceImpl;
import mz.co.mefemasys.xicola_backend.security.UtilizadorAuthenticationFilter;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private UtilizadorAuthenticationFilter userAuthenticationFilter;

    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            "/login", // url para fazer login
            "/oauth2/**", // url para login com google
            "/" // url para a página inicial
    };

    // Endpoints que requerem autenticação para serem acessados
    public static final String[] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/create/**",
            "/geral/**"
    };

    // Endpoints que só podem ser acessados por estudantes
    public static final String[] ENDPOINTS_ALUNO = {
            "/utilizadores/alunos/aluno/**",
            "/cursos/inscricoes/**",
            "/aulas/horarios/**",
            "/notas/**",
            "/disciplinas/aluno/**"
    };

    // Endpoints que só podem ser acessados por professores
    public static final String[] ENDPOINTS_PROFESSOR = {
            "/utilizadores/professores/professor/**",
            "/aulas/**",
            "/avaliacoes/**",
            "/notas/lancamento/**",
            "/disciplinas/professor/**"
    };

    // Endpoints que só podem ser acessados pelo departamento pedagógico
    public static final String[] ENDPOINTS_PEDAGOGICO = {
            "/pedagogico/**",
            "/utilizadores/alunos/**",
            "/utilizadores/professores/**",
            "/cursos/**",
            "/disciplinas/**",
            "/matriculas/**",
            "/relatorios/pedagogico/**"
    };

    // Endpoints que só podem ser acessados pelo departamento financeiro
    public static final String[] ENDPOINTS_FINANCEIRO = {
            "/financeiro/**",
            "/utilizadores/funcionario/funcionario/**",
            "/pagamentos/**",
            "/relatorios/financeiro/**",
            "/orcamentos/**",
            "/contabilidade/**"
    };

    // Endpoints que só podem ser acessados pelo departamento de aquisições
    public static final String[] ENDPOINTS_AQUISICOES = {
            "/aquisicoes/**",
            "/fornecedores/**",
            "/estoque/**",
            "/pedidos/**",
            "/relatorios/aquisicoes/**"
    };

    // Endpoints que só podem ser acessados pelo departamento de recursos humanos
    public static final String[] ENDPOINTS_RH = {
            "/rh/**",
            "/utilizadores/funcionarios/**",
            "/candidatos/**",
            "/processos_seletivos/**",
            "/beneficios/**",
            "/folha_pagamento/**"
    };

    // Endpoints que só podem ser acessados pela administração
    public static final String[] ENDPOINTS_ADMIN = {
            "/create/user",
            "/admin/**",
            "/relatorios/**",
            "/configuracoes/**",
            "/**"
    };

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
                                .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
                                .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRATOR")
                                .requestMatchers(ENDPOINTS_ALUNO).hasRole("ALUNO")
                                .requestMatchers(ENDPOINTS_PROFESSOR).hasRole("PROFESSOR")
                                .requestMatchers(ENDPOINTS_FINANCEIRO).hasRole("FINANCEIRO")
                                .requestMatchers(ENDPOINTS_PEDAGOGICO).hasRole("PEDAGOGICO")
                                .requestMatchers(ENDPOINTS_AQUISICOES).hasRole("AQUISICOES")
                                .requestMatchers(ENDPOINTS_RH).hasRole("RH")
                                .anyRequest().denyAll())
                .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
