package com.sistema.estoque.controller;

import com.sistema.estoque.entity.Produto;
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

    // 🔍 Novos endpoints de busca
    @GetMapping("/buscar/nome/{nome}")
    public ResponseEntity<List<Produto>> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(service.buscarPorNome(nome));
    }

    @GetMapping("/buscar/categoria/{categoria}")
    public ResponseEntity<List<Produto>> buscarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(service.buscarPorCategoria(categoria));
    }

    @GetMapping("/buscar/estoque-baixo")
    public ResponseEntity<List<Produto>> buscarComEstoqueBaixo() {
        return ResponseEntity.ok(service.buscarComEstoqueBaixo());
    }

    @GetMapping("/buscar/faixa-preco")
    public ResponseEntity<List<Produto>> buscarPorFaixaDePreco(
            @RequestParam Double min,
            @RequestParam Double max) {
        return ResponseEntity.ok(service.buscarPorFaixaDePreco(min, max));
    }
}
