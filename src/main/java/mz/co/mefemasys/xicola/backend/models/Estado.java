package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;

import lombok.Getter;

import lombok.Setter;

import org.hibernate.annotations.OnDelete;

import org.hibernate.annotations.OnDeleteAction;

import java.util.logging.Logger;

@Getter
@Setter
@Entity
@Table(name = "estado", schema = "public")
public class Estado {

    private static final Logger LOG = Logger.getLogger(Estado.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "descricao", nullable = false, length = Integer.MAX_VALUE)
    private String descricao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tipo_estado", nullable = false)
    private TipoEstado tipoEstado;

}
