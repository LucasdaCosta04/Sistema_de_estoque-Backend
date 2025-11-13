package com.sistema.estoque.dto;

import com.sistema.estoque.entity.TipoMovimentacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimentacaoDTO {

    private Long id;

    @NotNull(message = "ID do produto é obrigatório")
    private Long produtoId;

    @NotNull(message = "Data é obrigatória")
    private LocalDate data;

    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser maior que zero")
    private Integer quantidade;

    @NotNull(message = "Tipo de movimentação é obrigatório")
    private TipoMovimentacao tipo;
}