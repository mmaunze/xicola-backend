package mz.co.mefemasys.xicola_backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "encarregado_educacao", schema = "public")
public class EncarregadoEducacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @ColumnDefault("nextval('encarregado_educacao_id_seq'::regclass)")
    @JoinColumn(name = "id", nullable = false)
    private Utilizador utilizador;

    @Size(max = 150)
    @NotNull
    @Column(name = "nome_completo", nullable = false, length = 150)
    private String nomeCompleto;

    @NotNull
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "distrito_nascimento")
    private Distrito distritoNascimento;

    @Size(max = 10, min = 8)
    @NotNull
    @Column(name = "sexo", nullable = false, length = 1)
    private String sexo;

    @Size(max = 78)
    @Column(name = "religiao", length = 78)
    private String religiao;

    @Size(max = 255)
    @Column(name = "local_trabalho")
    private String localTrabalho;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "sector_trabalho", nullable = false)
    private SectorTrabalho sectorTrabalho;

    @Size(max = 255)
    @Column(name = "endereco")
    private String endereco;

    @Size(max = 75)
    @Column(name = "email", length = 75)
    private String email;

    @Size(max = 3)
    @Column(name = "grupo_sanguineo", length = 3)
    private String grupoSanguineo;

    @NotNull
    @Column(name = "numero_telefone_principal", nullable = false)
    private Long numeroTelefonePrincipal;

    @Column(name = "numero_telefone_alternativo")
    private Long numeroTelefoneAlternativo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "estado", nullable = false)
    private Estado estado;

}