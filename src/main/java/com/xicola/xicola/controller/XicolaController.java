package com.xicola.xicola.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xicola.xicola.model.dto.LoginUserDto;
import com.xicola.xicola.model.dto.RecoveryJwtTokenDto;
import com.xicola.xicola.service.UtilizadorService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class XicolaController {

    private final UtilizadorService userService;

    @GetMapping()
    public List<Map<String, String>> getRoutes() {
        return Arrays.asList(
                Map.of("name", "Alunos", "route", "/alunos"),
                Map.of("name", "Professores", "route", "/professores"),
                Map.of("name", "Disciplinas", "route", "/disciplinas"),
                Map.of("name", "Turmas", "route", "/turmas"),
                Map.of("name", "Salas", "route", "/salas"),
                Map.of("name", "Financeiro", "route", "/financeiro"),
                Map.of("name", "Diretoria", "route", "/diretoria"),
                Map.of("name", "Pedagogia", "route", "/pedagogia"),
                Map.of("name", "Eventos", "route", "/eventos"),
                Map.of("name", "Comunicados", "route", "/comunicados"));
    }

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
