package com.xicola.xicola.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.io.Serial;
import lombok.Data;

@Data
@Embeddable
public class EncarregadoAlunoId implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -4054304937289448834L;
    @NotNull
    @Column(name = "encarregado", nullable = false)
    private Integer encarregado;

    @NotNull
    @Column(name = "aluno", nullable = false)
    private Integer aluno;

}