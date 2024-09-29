package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sala", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "sala_nome_sala_key", columnNames = { "nome_sala" })
})
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nome_sala", nullable = false, length = 100)
    private String nomeSala;

    @NotNull
    @Column(name = "capacidade", nullable = false)
    private Integer capacidade;

}