package com.sistema.estoque.controller;

import com.sistema.estoque.dto.MovimentacaoDTO;
import com.sistema.estoque.entity.Movimentacao;
import com.sistema.estoque.service.MovimentacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operações de movimentação de estoque
 * Responsável pelo CRUD de movimentações (entradas e saídas) e integração com frontend
 */
@RestController
@RequestMapping("/api/movimentacoes")
@CrossOrigin(origins = "http://localhost:4200") // Permite requisições do frontend Angular
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    /**
     * Injeção de dependência via construtor para o serviço de movimentação
     */
    public MovimentacaoController(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    /**
     * Cria uma nova movimentação no sistema
     * @param movimentacaoDTO DTO com dados da movimentação a ser criada
     * @return ResponseEntity com a movimentação criada ou erro de validação
     */
    @PostMapping
    public ResponseEntity<?> criarMovimentacao(@Valid @RequestBody MovimentacaoDTO movimentacaoDTO) {
        try {
            // Chama o serviço para criar a movimentação e atualizar o estoque
            Movimentacao movimentacao = movimentacaoService.criarMovimentacao(movimentacaoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(movimentacao);
        } catch (Exception e) {
            // Retorna erro 400 em caso de falha na validação de negócio
            return ResponseEntity.badRequest().body("Erro ao criar movimentação: " + e.getMessage());
        }
    }

    /**
     * Lista todas as movimentações do sistema ordenadas por data (mais recente primeiro)
     * @return Lista de todas as movimentações
     */
    @GetMapping
    public ResponseEntity<List<Movimentacao>> listarTodasMovimentacoes() {
        List<Movimentacao> movimentacoes = movimentacaoService.listarTodas();
        return ResponseEntity.ok(movimentacoes);
    }

    /**
     * Busca uma movimentação específica pelo ID
     * @param id ID da movimentação a ser buscada
     * @return Movimentação encontrada ou erro 404 se não existir
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarMovimentacaoPorId(@PathVariable Long id) {
        try {
            Movimentacao movimentacao = movimentacaoService.buscarPorId(id);
            return ResponseEntity.ok(movimentacao);
        } catch (Exception e) {
            // Retorna erro 404 se a movimentação não for encontrada
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Lista todas as movimentações de um produto específico
     * @param produtoId ID do produto para filtrar as movimentações
     * @return Lista de movimentações do produto ordenadas por data
     */
    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<Movimentacao>> listarMovimentacoesPorProduto(@PathVariable Long produtoId) {
        List<Movimentacao> movimentacoes = movimentacaoService.listarPorProduto(produtoId);
        return ResponseEntity.ok(movimentacoes);
    }

    /**
     * Atualiza uma movimentação existente
     * @param id ID da movimentação a ser atualizada
     * @param movimentacaoDTO DTO com os novos dados da movimentação
     * @return Movimentação atualizada ou erro de validação
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarMovimentacao(@PathVariable Long id, @Valid @RequestBody MovimentacaoDTO movimentacaoDTO) {
        try {
            // Atualiza a movimentação e recalcula automaticamente o estoque
            Movimentacao movimentacao = movimentacaoService.atualizarMovimentacao(id, movimentacaoDTO);
            return ResponseEntity.ok(movimentacao);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar movimentação: " + e.getMessage());
        }
    }

    /**
     * Exclui uma movimentação do sistema
     * @param id ID da movimentação a ser excluída
     * @return Resposta vazia com status 204 ou erro 404 se não encontrada
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirMovimentacao(@PathVariable Long id) {
        try {
            // Exclui a movimentação e reverte automaticamente o estoque
            movimentacaoService.excluirMovimentacao(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}