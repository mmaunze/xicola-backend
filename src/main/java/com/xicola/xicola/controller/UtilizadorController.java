package com.xicola.xicola.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xicola.xicola.model.Utilizador;
import com.xicola.xicola.model.dto.LoginUserDto;
import com.xicola.xicola.model.dto.RecoveryJwtTokenDto;
import com.xicola.xicola.service.UtilizadorService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/utilizadores")
@RequiredArgsConstructor
public class UtilizadorController {

    private final UtilizadorService userService;

    @PostMapping("/create/user")
    public ResponseEntity<Void> createUser(@RequestBody Utilizador utilizador) {
        userService.create(utilizador);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}