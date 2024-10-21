package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;

import jakarta.validation.constraints.Size;

import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "provincia", schema = "public", uniqueConstraints = {
    @UniqueConstraint(name = "provincia_nome_provincia_key", columnNames = {"nome_provincia"})
})
public class Provincia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 30)
    @Column(name = "nome_provincia", length = 30)
    private String nomeProvincia;

}
