package mz.co.mefemasys.xicola.backend.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mz.co.mefemasys.xicola.backend.models.ERole;
import mz.co.mefemasys.xicola.backend.models.Role;
import mz.co.mefemasys.xicola.backend.models.Utilizador;
import mz.co.mefemasys.xicola.backend.payload.request.LoginRequest;
import mz.co.mefemasys.xicola.backend.payload.request.SignupRequest;
import mz.co.mefemasys.xicola.backend.payload.response.JwtResponse;
import mz.co.mefemasys.xicola.backend.payload.response.MessageResponse;
import mz.co.mefemasys.xicola.backend.repository.RoleRepository;
import mz.co.mefemasys.xicola.backend.repository.UtilizadorRepository;
import mz.co.mefemasys.xicola.backend.security.jwt.JwtUtils;
import mz.co.mefemasys.xicola.backend.security.services.UtilizadorDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/autenticacao")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UtilizadorRepository utilizadorRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UtilizadorDetailsImpl userDetails = (UtilizadorDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (utilizadorRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username existente!"));
        }

        if (utilizadorRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email existente!"));
        }

        // Create new utilizador's account
        Utilizador utilizador = new Utilizador(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                    case "estudante":
                        Role estudanteRole = roleRepository.findByName(ERole.ROLE_ESTUDANTE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(estudanteRole);
                        break;
                        case "professor":
                        Role professorRole = roleRepository.findByName(ERole.ROLE_PROFESSOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(professorRole);
                        break;

                        case "bibliotecario":
                        Role bibliotecarioRole = roleRepository.findByName(ERole.ROLE_BIBLIOTECARIO)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(bibliotecarioRole);
                        break;

                        case "pedagogico": 
                        Role pedagogicoRole = roleRepository.findByName(ERole.ROLE_PEDAGOGICO).orElseThrow(()-> new  RuntimeException("Error: Role is not found."));
                        roles.add(pedagogicoRole);
                        break;

                        case "financeiro":
                        Role financeiroRole = roleRepository.findByName(ERole.ROLE_FINANCEIRO).orElseThrow(()-> new RuntimeException("Error: Role is not found."));
                        roles.add(financeiroRole);
                        break;

                        case "director":
                        Role directorRole = roleRepository.findByName(ERole.ROLE_DIRECTOR).orElseThrow(()-> new RuntimeException("Error: Role is not found."));
                        roles.add(directorRole);
                        break;

                        case "encarregado": 
                        Role encarregadoRole = roleRepository.findByName(ERole.ROLE_ENCARREGADO).orElseThrow(()-> new RuntimeException("Error: Role is not found."));
                        roles.add(encarregadoRole);
                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        utilizador.setRoles(roles);
        utilizadorRepository.save(utilizador);

        return ResponseEntity.ok(new MessageResponse("Utilizador Registado com sucesso!"));
    }
}
