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
@Table(name = "area_cientifica", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "area_cientifica_descricao_key", columnNames = {"descricao"})
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