package com.xicola.xicola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "categoria_financeira", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "categoria_financeira_descricao_key", columnNames = {"descricao"})
})
public class CategoriaFinanceira {
    @Id

    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "descricao", nullable = false, length = 100)
    private String descricao;

}