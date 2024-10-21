package mz.co.mefemasys.xicola.backend.payload.response;

import java.util.List;

import java.util.logging.Logger;

public class JwtResponse {

    private static final Logger LOG = Logger.getLogger(JwtResponse.class.getName());

    private final List<String> roles;

    private String token;

    private String type = "Bearer";

    private Long id;

    private String nome;

    private String username;

    private String email;

    public JwtResponse(String accessToken, Long id, String nome, String username, String email, List<String> roles) {
        this.token = accessToken;

        this.id = id;

        this.nome = nome;

        this.username = username;

        this.email = email;

        this.roles = roles;

    }

    public String getAccessToken() {
        return token;

    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;

    }

    public String getTokenType() {
        return type;

    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;

    }

    public Long getId() {
        return id;

    }

    public void setId(Long id) {
        this.id = id;

    }

    public String getEmail() {
        return email;

    }

    public void setEmail(String email) {
        this.email = email;

    }

    public String getNome() {
        return nome;

    }

    public void setNome(String nome) {
        this.nome = nome;

    }

    public String getUsername() {
        return username;

    }

    public void setUsername(String username) {
        this.username = username;

    }

    public List<String> getRoles() {
        return roles;

    }
}
