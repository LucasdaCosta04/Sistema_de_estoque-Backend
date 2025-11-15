package com.sistema.estoque.repository;

import com.sistema.estoque.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNomeIgnoreCase(String nome);
    Optional<Categoria> findByNomeIgnoreCase(String nome);
}
