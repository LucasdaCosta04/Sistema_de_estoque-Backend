package com.sistema.estoque.repository;

import com.sistema.estoque.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // aqui você pode adicionar queries customizadas depois
}
