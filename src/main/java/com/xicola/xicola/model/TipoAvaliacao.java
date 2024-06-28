package com.xicola.xicola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "tipo_avaliacao", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "tipo_avaliacao_descricao_key", columnNames = {"descricao"})
})
public class TipoAvaliacao {
    @Id
    @ColumnDefault("nextval('tipo_avaliacao_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "descricao", nullable = false, length = Integer.MAX_VALUE)
    private String descricao;

}