package com.sistema.estoque.controller;

import com.sistema.estoque.dto.MovimentacaoDTO;
import com.sistema.estoque.model.Movimentacao;
import com.sistema.estoque.service.MovimentacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimentacoes")
@CrossOrigin(origins = "http://localhost:4200")
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    public MovimentacaoController(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @PostMapping
    public ResponseEntity<?> criarMovimentacao(@Valid @RequestBody MovimentacaoDTO movimentacaoDTO) {
        try {
            Movimentacao movimentacao = movimentacaoService.criarMovimentacao(movimentacaoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(movimentacao);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao criar movimentação: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Movimentacao>> listarTodasMovimentacoes() {
        List<Movimentacao> movimentacoes = movimentacaoService.listarTodas();
        return ResponseEntity.ok(movimentacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarMovimentacaoPorId(@PathVariable Long id) {
        try {
            Movimentacao movimentacao = movimentacaoService.buscarPorId(id);
            return ResponseEntity.ok(movimentacao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<Movimentacao>> listarMovimentacoesPorProduto(@PathVariable Long produtoId) {
        List<Movimentacao> movimentacoes = movimentacaoService.listarPorProduto(produtoId);
        return ResponseEntity.ok(movimentacoes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarMovimentacao(@PathVariable Long id, @Valid @RequestBody MovimentacaoDTO movimentacaoDTO) {
        try {
            Movimentacao movimentacao = movimentacaoService.atualizarMovimentacao(id, movimentacaoDTO);
            return ResponseEntity.ok(movimentacao);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar movimentação: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirMovimentacao(@PathVariable Long id) {
        try {
            movimentacaoService.excluirMovimentacao(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}