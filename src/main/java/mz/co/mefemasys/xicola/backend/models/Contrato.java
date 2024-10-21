package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;

import lombok.Getter;

import lombok.Setter;

import org.hibernate.annotations.OnDelete;

import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

import java.time.LocalDate;

import java.util.logging.Logger;

@Getter
@Setter
@Entity
@Table(name = "contrato", schema = "public")
public class Contrato {

    private static final Logger LOG = Logger.getLogger(Contrato.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Size(max = 100)
    @NotNull
    @Column(name = "tipo", nullable = false, length = 100)
    private String tipo;

    @NotNull
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @NotNull
    @Column(name = "valor_total", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorTotal;

    @Size(max = 255)
    @NotNull
    @Column(name = "fornecedor", nullable = false)
    private String fornecedor;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "responsavel", nullable = false)
    private Funcionario responsavel;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "estado", nullable = false)
    private Estado estado;

}
