package com.xicola.xicola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "folha_pagamento", schema = "public")
public class FolhaPagamento {
    @Id

    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "funcionario", nullable = false)
    private Funcionario funcionario;

    @NotNull
    @Column(name = "salario_bruto", nullable = false, precision = 10, scale = 2)
    private BigDecimal salarioBruto;

    @Column(name = "descontos", precision = 10, scale = 2)
    private BigDecimal descontos;

    @NotNull
    @Column(name = "salario_liquido", nullable = false, precision = 10, scale = 2)
    private BigDecimal salarioLiquido;

    @NotNull
    @Column(name = "mes_referencia", nullable = false)
    private LocalDate mesReferencia;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "data_pagamento", nullable = false)
    private Instant dataPagamento;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "estado", nullable = false)
    private Estado estado;

}