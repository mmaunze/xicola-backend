package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sector_trabalho", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "sector_trabalho_descricao_key", columnNames = {"descricao"})
})
public class SectorTrabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 150)
    @NotNull
    @Column(name = "descricao", nullable = false, length = 150)
    private String descricao;

}
