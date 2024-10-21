package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;

import lombok.Getter;

import lombok.Setter;

import org.hibernate.annotations.OnDelete;

import org.hibernate.annotations.OnDeleteAction;

import java.util.logging.Logger;

@Getter
@Setter
@Entity
@Table(name = "participante_evento", schema = "public")
public class ParticipanteEvento {

    private static final Logger LOG = Logger.getLogger(ParticipanteEvento.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "evento", nullable = false)
    private Evento evento;

    @Size(max = 150)
    @NotNull
    @Column(name = "nome_participante", nullable = false, length = 150)
    private String nomeParticipante;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tipo_participante", nullable = false)
    private TipoPessoa tipoParticipante;

}
