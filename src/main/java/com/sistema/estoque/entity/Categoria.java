package com.sistema.estoque.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidade que representa uma categoria de produtos no sistema de estoque.
 *
 * Cada categoria pode conter vários produtos, e é utilizada como forma de
 * classificação e organização dentro da aplicação.
 *
 * A entidade é persistida na tabela "categorias" e possui uma restrição
 * de unicidade no campo "nome".
 */
@Entity
@Table(name = "categorias", uniqueConstraints = @UniqueConstraint(columnNames = "nome"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    /**
     * Identificador único da categoria.
     * Gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}

