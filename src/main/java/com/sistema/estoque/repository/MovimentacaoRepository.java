package com.sistema.estoque.repository;

import com.sistema.estoque.entity.Movimentacao;
import com.sistema.estoque.entity.TipoMovimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {

    List<Movimentacao> findByProdutoIdOrderByDataDesc(Long produtoId);

    List<Movimentacao> findAllByOrderByDataDesc();

    @Query("SELECT m FROM Movimentacao m WHERE m.produto.id = :produtoId AND m.tipo = 'ENTRADA'")
    List<Movimentacao> findEntradasByProdutoId(@Param("produtoId") Long produtoId);

    @Query("SELECT m FROM Movimentacao m WHERE m.produto.id = :produtoId AND m.tipo = 'SAIDA'")
    List<Movimentacao> findSaidasByProdutoId(@Param("produtoId") Long produtoId);

    @Query("SELECT m.produto, SUM(m.quantidade) FROM Movimentacao m WHERE m.tipo = :tipo GROUP BY m.produto ORDER BY SUM(m.quantidade) DESC")
    List<Object[]> findProdutoMaisMovimentadoPorTipo(@Param("tipo") TipoMovimentacao tipo);
}