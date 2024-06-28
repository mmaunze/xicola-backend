package com.xicola.xicola.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class XicolaController {

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
                Map.of("name", "Comunicados", "route", "/comunicados")
        );
    }
}
