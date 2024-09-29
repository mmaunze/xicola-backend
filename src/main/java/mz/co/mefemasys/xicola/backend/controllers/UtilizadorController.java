package mz.co.mefemasys.xicola.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import mz.co.mefemasys.xicola.backend.models.Utilizador;
import mz.co.mefemasys.xicola.backend.models.dto.LoginUserDto;
import mz.co.mefemasys.xicola.backend.models.dto.RecoveryJwtTokenDto;
import mz.co.mefemasys.xicola.backend.service.UtilizadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

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