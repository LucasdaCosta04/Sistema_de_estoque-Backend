package com.sistema.estoque.repository;

import com.sistema.estoque.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Para relatório de lista de preços
    List<Produto> findByOrderByNomeAsc();

    // Para relatório de produtos abaixo do mínimo
    @Query("SELECT p FROM Produto p WHERE p.quantidadeEstoque < p.quantidadeMinima")
    List<Produto> findProdutosAbaixoDoMinimo();

    // Para relatório de produtos por categoria
    @Query("SELECT p.categoria, COUNT(p) FROM Produto p GROUP BY p.categoria")
    List<Object[]> countProdutosPorCategoria();
}