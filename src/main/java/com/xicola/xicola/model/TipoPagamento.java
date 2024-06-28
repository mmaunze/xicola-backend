package com.xicola.xicola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "tipo_pagamento", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "tipo_pagamento_descricao_key", columnNames = {"descricao"})
})
public class TipoPagamento {
    @Id
    @ColumnDefault("nextval('tipo_pagamento_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 80)
    @Column(name = "descricao", length = 80)
    private String descricao;

}