package com.xicola.xicola.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "area_cientifica", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "area_cientifica_descricao_key", columnNames = { "descricao" })
})
public class AreaCientifica {
    @Id

    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 150)
    @NotNull
    @Column(name = "descricao", nullable = false, length = 150)
    private String descricao;

}