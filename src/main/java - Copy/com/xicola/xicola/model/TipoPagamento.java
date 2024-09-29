package mz.co.mefemasys.xicola_backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tipo_pagamento", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "tipo_pagamento_descricao_key", columnNames = { "descricao" })
})
public class TipoPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 80)
    @Column(name = "descricao", length = 80)
    private String descricao;

}