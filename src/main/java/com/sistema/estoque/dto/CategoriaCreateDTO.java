package com.sistema.estoque.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO utilizado para receber os dados necessários
 * para criação ou atualização de uma categoria.
 *
 * Esse DTO é usado apenas como entrada (request),
 * garantindo que apenas os campos permitidos sejam enviados
 * pelo cliente.
 */
@Getter @Setter
public class CategoriaCreateDTO {

    /**
     * Nome da categoria.
     * Campo obrigatório e validado com @NotBlank
     * para impedir que seja enviado vazio ou nulo.
     */
    @NotBlank(message = "O nome da categoria é obrigatório.")
    private String nome;

    /**
     * Descrição opcional da categoria.
     * Utilizado apenas para melhorar entendimento e organização.
     */
    private String descricao;
}
