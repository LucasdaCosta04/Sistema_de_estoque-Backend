package com.sistema.estoque.controller;

import com.sistema.estoque.entity.Produto;
import com.sistema.estoque.entity.TipoMovimentacao;
import com.sistema.estoque.repository.MovimentacaoRepository;
import com.sistema.estoque.repository.ProdutoRepository;
import com.sistema.estoque.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProdutoController {

    private final ProdutoService service;
    private final ProdutoRepository produtoRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    public ProdutoController(
            ProdutoService service,
            ProdutoRepository produtoRepository,
            MovimentacaoRepository movimentacaoRepository
    ) {
        this.service = service;
        this.produtoRepository = produtoRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    // ==========================
    // 🧱 CRUD BÁSICO
    // ==========================

    @PostMapping
    public ResponseEntity<Produto> adicionar(@RequestBody Produto p) {
        Produto criado = service.adicionar(p);
        return ResponseEntity.ok(criado);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listar() {
        return ResponseEntity.ok(produtoRepository.findByOrderByNomeAsc());
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

    // ==========================
    // 🔍 BUSCAS PERSONALIZADAS
    // ==========================

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
        List<Produto> produtos = service.buscarComEstoqueBaixo();
        produtos.sort(Comparator.comparing(Produto::getNome, String.CASE_INSENSITIVE_ORDER));
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/buscar/faixa-preco")
    public ResponseEntity<List<Produto>> buscarPorFaixaDePreco(
            @RequestParam Double min,
            @RequestParam Double max) {
        List<Produto> produtos = service.buscarPorFaixaDePreco(min, max);
        produtos.sort(Comparator.comparing(Produto::getNome, String.CASE_INSENSITIVE_ORDER));
        return ResponseEntity.ok(produtos);
    }

    // ==========================
    // 📊 RELATÓRIOS
    // ==========================

    // 📄 Lista de Preços
    @GetMapping("/relatorio/lista-precos")
    public ResponseEntity<List<Produto>> getListaPrecos() {
        List<Produto> produtos = produtoRepository.findByOrderByNomeAsc();
        return ResponseEntity.ok(produtos);
    }

    // 💰 Balanço Físico/Financeiro
    @GetMapping("/relatorio/balanco")
    public ResponseEntity<Map<String, Object>> getBalanco() {
        List<Produto> produtos = produtoRepository.findByOrderByNomeAsc();
        double valorTotalEstoque = 0.0;

        List<Map<String, Object>> produtosComTotal = new ArrayList<>();

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

    // ⚠️ Produtos abaixo do mínimo
    @GetMapping("/relatorio/estoque-minimo")
    public ResponseEntity<List<Produto>> getProdutosAbaixoMinimo() {
        List<Produto> produtos = produtoRepository.findProdutosAbaixoDoMinimo();
        produtos.sort(Comparator.comparing(Produto::getNome, String.CASE_INSENSITIVE_ORDER));
        return ResponseEntity.ok(produtos);
    }

    // 📦 Quantidade de produtos por categoria
    @GetMapping("/relatorio/produtos-por-categoria")
    public ResponseEntity<List<Object[]>> getProdutosPorCategoria() {
        List<Object[]> resultados = produtoRepository.countProdutosPorCategoria();
        // Já vem agrupado, então a ordenação pode ser feita manualmente se necessário
        resultados.sort(Comparator.comparing(o -> o[0].toString(), String.CASE_INSENSITIVE_ORDER));
        return ResponseEntity.ok(resultados);
    }

    // 🔁 Produto com mais entrada e saída
    @GetMapping("/relatorio/produtos-mais-movimentados")
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