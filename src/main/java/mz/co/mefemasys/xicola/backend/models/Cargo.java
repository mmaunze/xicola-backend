package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;

import lombok.Getter;

import lombok.Setter;

import java.util.logging.Logger;

@Getter
@Setter
@Entity
@Table(name = "cargo", schema = "public", uniqueConstraints = {
    @UniqueConstraint(name = "cargo_descricao_key", columnNames = {"descricao"})
})
public class Cargo {

    private static final Logger LOG = Logger.getLogger(Cargo.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "descricao", nullable = false, length = 50)
    private String descricao;

}
