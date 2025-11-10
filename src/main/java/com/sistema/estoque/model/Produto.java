package com.sistema.estoque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String nome;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private Double precoUnitario;

    @Column(length = 50)
    private String unidade;

    @NotNull
    @Min(0)
    private Integer quantidadeEstoque;

    @NotNull
    @Min(0)
    private Integer quantidadeMinima;

    @NotNull
    @Min(0)
    private Integer quantidadeMaxima;

    private String categoria;
}