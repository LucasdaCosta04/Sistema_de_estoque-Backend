package com.sistema.estoque.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categorias", uniqueConstraints = @UniqueConstraint(columnNames = "nome"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100, unique = true)
    private String nome;

    @Column(length = 250)
    private String descricao;

    @Column(nullable = false)
    private Instant criadoEm = Instant.now();

    @OneToMany(
            mappedBy = "categoria",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<Produto> produtos = new HashSet<>();
}