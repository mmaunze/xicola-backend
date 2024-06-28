package com.xicola.xicola.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "disciplina", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "disciplina_nome_disciplina_key", columnNames = {"nome_disciplina"})
})
public class Disciplina {
    @Id
    @ColumnDefault("nextval('disciplina_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nome_disciplina", nullable = false, length = 100)
    private String nomeDisciplina;

}