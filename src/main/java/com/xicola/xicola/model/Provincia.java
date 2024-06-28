package com.xicola.xicola.model;

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

    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 30)
    @Column(name = "nome_provincia", length = 30)
    private String nomeProvincia;

}