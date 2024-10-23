package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "metodo_pagamento", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "metodo_pagamento_descricao_key", columnNames = {"descricao"})
})
public class MetodoPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 80)
    @Column(name = "descricao", length = 80)
    private String descricao;

}
