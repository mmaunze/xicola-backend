package mz.co.mefemasys.xicola.backend.models;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private ERole name;

    public Role() {

    }

    public Role(ERole name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}