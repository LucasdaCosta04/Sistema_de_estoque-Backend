package com.sistema.estoque.controller;

import com.sistema.estoque.model.Produto;
import com.sistema.estoque.repository.MovimentacaoRepository;
import com.sistema.estoque.repository.ProdutoRepository;
import com.sistema.estoque.model.TipoMovimentacao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/relatorios")
@CrossOrigin(origins = "http://localhost:4200")
public class RelatorioController {

    private final ProdutoRepository produtoRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public RelatorioController(ProdutoRepository produtoRepository, MovimentacaoRepository movimentacaoRepository) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    // Lista de Preços (usa mesmo endpoint do ProdutoController)
    @GetMapping("/lista-precos")
    public ResponseEntity<List<Produto>> getListaPrecos() {
        List<Produto> produtos = produtoRepository.findByOrderByNomeAsc();
        return ResponseEntity.ok(produtos);
    }

    // Balanço Físico/Financeiro
    @GetMapping("/balanco")
    public ResponseEntity<Map<String, Object>> getBalanco() {
        List<Produto> produtos = produtoRepository.findAll();
        double valorTotalEstoque = 0.0;

        List<Map<String, Object>> produtosComTotal = new java.util.ArrayList<>();

        for (Produto produto : produtos) {
            double valorTotalProduto = produto.getPrecoUnitario() * produto.getQuantidadeEstoque();
            valorTotalEstoque += valorTotalProduto;

            Map<String, Object> produtoMap = new HashMap<>();
            produtoMap.put("produto", produto);
            produtoMap.put("valorTotal", valorTotalProduto);
            produtosComTotal.add(produtoMap);
        }

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("produtos", produtosComTotal);
        resultado.put("valorTotalEstoque", valorTotalEstoque);

        return ResponseEntity.ok(resultado);
    }

    // Produtos abaixo do mínimo (usa mesmo endpoint do ProdutoController)
    @GetMapping("/estoque-minimo")
    public ResponseEntity<List<Produto>> getProdutosAbaixoMinimo() {
        List<Produto> produtos = produtoRepository.findProdutosAbaixoDoMinimo();
        return ResponseEntity.ok(produtos);
    }

    // Produtos por categoria (usa mesmo endpoint do ProdutoController)
    @GetMapping("/produtos-por-categoria")
    public ResponseEntity<List<Object[]>> getProdutosPorCategoria() {
        List<Object[]> resultados = produtoRepository.countProdutosPorCategoria();
        return ResponseEntity.ok(resultados);
    }

    // Produtos mais movimentados (exclusivo do RelatorioController)
    @GetMapping("/produtos-mais-movimentados")
    public ResponseEntity<Map<String, Object>> getProdutosMaisMovimentados() {
        List<Object[]> maisEntradas = movimentacaoRepository.findProdutoMaisMovimentadoPorTipo(TipoMovimentacao.ENTRADA);
        List<Object[]> maisSaidas = movimentacaoRepository.findProdutoMaisMovimentadoPorTipo(TipoMovimentacao.SAIDA);

        Map<String, Object> resultado = new HashMap<>();

        if (!maisEntradas.isEmpty() && maisEntradas.get(0) != null) {
            resultado.put("produtoMaisEntrada", maisEntradas.get(0));
        }

        if (!maisSaidas.isEmpty() && maisSaidas.get(0) != null) {
            resultado.put("produtoMaisSaida", maisSaidas.get(0));
        }

        return ResponseEntity.ok(resultado);
    }
}