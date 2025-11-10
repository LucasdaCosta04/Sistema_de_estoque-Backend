package com.sistema.estoque.controller;

import com.sistema.estoque.model.Produto;
import com.sistema.estoque.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    // CRUD original
    @PostMapping
    public ResponseEntity<Produto> adicionar(@RequestBody Produto p) {
        Produto criado = service.adicionar(p);
        return ResponseEntity.ok(criado);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> editar(@PathVariable Long id, @RequestBody Produto p) {
        return ResponseEntity.ok(service.editar(id, p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reajuste/{percentual}")
    public ResponseEntity<Void> reajustar(@PathVariable double percentual) {
        service.reajustarPrecos(percentual);
        return ResponseEntity.noContent().build();
    }

    // Novos endpoints para relatórios e integração
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/ordenados")
    public ResponseEntity<List<Produto>> listarOrdenados() {
        return ResponseEntity.ok(service.listarOrdenadoPorNome());
    }

    @GetMapping("/abaixo-minimo")
    public ResponseEntity<List<Produto>> listarAbaixoMinimo() {
        return ResponseEntity.ok(service.listarProdutosAbaixoDoMinimo());
    }

    @GetMapping("/por-categoria")
    public ResponseEntity<List<Object[]>> contarPorCategoria() {
        return ResponseEntity.ok(service.contarProdutosPorCategoria());
    }
}