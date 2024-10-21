package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cargo", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "cargo_descricao_key", columnNames = { "descricao" })
})
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "descricao", nullable = false, length = 50)
    private String descricao;
    private static final Logger LOG = Logger.getLogger(Cargo.class.getName());

}