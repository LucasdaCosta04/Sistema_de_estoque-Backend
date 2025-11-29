package com.sistema.estoque.controller;

import com.sistema.estoque.dto.ProdutoDTO;
import com.sistema.estoque.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST responsável por gerenciar operações relacionadas a produtos.
 * Expõe endpoints para CRUD e reajuste de preços.
 */
@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProdutoController {

    private final ProdutoService service;

     /**
     * Construtor para injeção de dependência do serviço de produtos.
     *
     * @param service serviço responsável pela regra de negócio de produtos
     */
    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

     /**
     * Endpoint para adicionar um novo produto ao sistema.
     *
     * @param dto objeto contendo os dados do produto a ser criado
     * @return ResponseEntity contendo o produto criado
     */
    @PostMapping
    public ResponseEntity<ProdutoDTO> adicionar(@RequestBody ProdutoDTO dto) {
        ProdutoDTO criado = service.adicionar(dto);
        return ResponseEntity.ok(criado);
    }

     /**
     * Endpoint para listar todos os produtos cadastrados.
     *
     * @return ResponseEntity contendo a lista de produtos
     */
    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

     /**
     * Endpoint para editar um produto existente.
     *
     * @param id  identificador único do produto
     * @param dto dados atualizados do produto
     * @return ResponseEntity contendo o produto atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> editar(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
        return ResponseEntity.ok(service.editar(id, dto));
    }

     /**
     * Endpoint para excluir um produto do sistema.
     *
     * @param id identificador do produto a ser removido
     * @return ResponseEntity sem conteúdo em caso de sucesso
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

     /**
     * Endpoint para reajustar o preço de todos os produtos com base em um percentual.
     *
     * @param percentual percentual de reajuste a ser aplicado (ex: 10 = 10%)
     * @return ResponseEntity sem conteúdo em caso de sucesso
     */
    @PostMapping("/reajuste/{percentual}")
    public ResponseEntity<Void> reajustar(@PathVariable double percentual) {
        service.reajustarPrecos(percentual);
        return ResponseEntity.noContent().build();
    }
}
