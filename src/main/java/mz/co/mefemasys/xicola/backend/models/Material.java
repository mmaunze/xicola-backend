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
@Table(name = "material", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "material_nome_material_key", columnNames = {"nome_material"})
})
public class Material {

    private static final Logger LOG = Logger.getLogger(Material.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tipo_material", nullable = false)
    private TipoMaterial tipoMaterial;

    @Size(max = 100)
    @NotNull
    @Column(name = "nome_material", nullable = false, length = 100)
    private String nomeMaterial;

    @Column(name = "quantidade")
    private Integer quantidade;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "estado", nullable = false)
    private Estado estado;

}
