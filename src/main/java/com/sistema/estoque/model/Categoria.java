package com.sistema.estoque.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "categorias", uniqueConstraints = @UniqueConstraint(columnNames = "nome"))
public class Categoria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String nome;

    @Column(length = 250)
    private String descricao;

    @Column(nullable = false)
    private Instant criadoEm = Instant.now();

    // getters e setters
}
