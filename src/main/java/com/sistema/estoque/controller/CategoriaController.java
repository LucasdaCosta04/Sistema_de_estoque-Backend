package com.sistema.estoque.controller;

import com.sistema.estoque.dto.CategoriaCreateDTO;
import com.sistema.estoque.dto.CategoriaResponseDTO;
import com.sistema.estoque.service.CategoriaService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Controller responsável por expor os endpoints REST relacionados à entidade Categoria.
 * Toda a regra de negócio é delegada ao CategoriaService.
 */
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    /**
     * Injeção de dependência do service responsável por tratar regras de negócio da Categoria.
     */
    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    /**
     * Endpoint para criar uma nova categoria.
     *
     * @param dto Objeto contendo os dados necessários para criação.
     * @return Categoria criada com URI no header.
     */
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> criarCategoria(@Valid @RequestBody CategoriaCreateDTO dto) {

        // Chama o service para criar a categoria
        CategoriaResponseDTO novaCategoria = categoriaService.criarCategoria(dto);

        // Gera a URI do recurso recém-criado
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novaCategoria.getId())
                .toUri();

        // Retorna status 201 Created com o corpo do objeto criado
        return ResponseEntity.created(location).body(novaCategoria);
    }
}
