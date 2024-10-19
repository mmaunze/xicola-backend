package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "aluno", schema = "public")

public class Aluno {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    private Utilizador utilizador;


    @Size(max = 100)
    @NotNull
    @Column(name = "nome_completo", nullable = false, length = 190)
    private String nomeCompleto;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "distrito_nascimento")
    private Distrito distritoNascimento;

    @Size(max = 12)
    @NotNull
    @Column(name = "sexo", nullable = false, length = 12)
    private String sexo;

    @Size(max = 13)
    @Column(name = "bilhete_identificacao")
    private String bilheteIdentificacao;

    @Size(max = 78)
    @Column(name = "religiao", length = 78)
    private String religiao;

    @Size(max = 5)
    @Column(name = "grupo_sanguineo", length = 5)
    private String grupoSanguineo;

    @Size(max = 255)
    @Column(name = "endereco")
    private String endereco;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "data_registo", nullable = false)
    private Instant dataRegisto;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "estado", nullable = false)
    private Estado estado;

    @Column(name = "escola_anterior", length = Integer.MAX_VALUE)
    private String escolaAnterior;

    @Size(max = 150)
    @Column(name = "nome_do_pai", length = 150)
    private String nomeDoPai;

    @Size(max = 150)
    @Column(name = "nome_da_mae", length = 150)
    private String nomeDaMae;

    @Column(name = "numero_telefone_principal")
    private Long numeroTelefonePrincipal;

}