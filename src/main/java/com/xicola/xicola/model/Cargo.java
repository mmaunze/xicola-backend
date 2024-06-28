package com.xicola.xicola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "cargo", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "cargo_descricao_key", columnNames = {"descricao"})
})
public class Cargo {
    @Id

    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "descricao", nullable = false, length = 50)
    private String descricao;

}