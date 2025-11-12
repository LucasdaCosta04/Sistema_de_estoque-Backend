# 🧮 Sistema de Estoque - Backend

### 🧑‍💻 Integrantes do Grupo:
- Lucas da Costa  
- Carlos Henrique Lohn  
- João Víctor Muniz da Silva  
- Rennan Rosa Guedes  
- André Leonardo da Silva  

---

## 📖 Descrição do Projeto

O **Sistema de Estoque** é uma aplicação backend desenvolvida em **Spring Boot**, com integração ao banco de dados **MySQL** e arquitetura baseada em **padrões de projeto (MVC e Service Layer Pattern)**.  
O objetivo principal é oferecer uma solução simples e eficiente para o **controle de produtos e movimentações de estoque**.

O sistema permite:
- Cadastrar, editar e excluir produtos;
- Registrar movimentações (entradas e saídas);
- Reajustar preços de forma percentual;
- Gerar relatórios como balanço financeiro e produtos abaixo do estoque mínimo.

---

## ⚙️ Tecnologias Utilizadas

| Categoria | Tecnologias |
|------------|--------------|
| Linguagem | Java 17 |
| Framework | Spring Boot 3.3.1 |
| Banco de Dados | MySQL |
| ORM | Spring Data JPA / Hibernate |
| Servidor | Tomcat embutido (Spring Boot) |
| Gerenciador de Dependências | Maven |
| Ferramentas de Teste | Postman |
| IDE | IntelliJ IDEA Ultimate |

---

## 🧩 Padrões de Projeto Aplicados

- **MVC (Model–View–Controller):**  
  Separação clara das camadas — `Entity`, `Controller`, `Service`, `Repository`.

- **Service Layer Pattern:**  
  Centraliza a lógica de negócio nas classes de serviço (`ProdutoService`, `MovimentacaoService`), deixando os controladores focados em lidar com as requisições HTTP.

---

## 🧱 Estrutura do Projeto

src/main/java/com/sistema/estoque
├── controller/
│ ├── ProdutoController.java
│ ├── MovimentacaoController.java
│ └── RelatorioController.java
├── dto/
│ └── MovimentacaoDTO.java
├── entity/
│ ├── Produto.java
│ ├── Movimentacao.java
│ └── TipoMovimentacao.java
├── repository/
│ ├── ProdutoRepository.java
│ └── MovimentacaoRepository.java
├── service/
│ ├── ProdutoService.java
│ └── MovimentacaoService.java
└── SistemaDeEstoqueApplication.java


---

## 🚀 Execução do Projeto

A aplicação é executada diretamente pelo IntelliJ IDEA:
1. Abrir o projeto.
2. Localizar a classe `SistemaDeEstoqueApplication`.
3. Clicar em **Run ▶️** para iniciar o servidor embutido.
4. O backend será iniciado em **http://localhost:8080**.

---

## 🔌 Endpoints REST

| Método | Endpoint | Descrição |
|---------|-----------|-----------|
| GET | `/api/produtos` | Lista todos os produtos |
| POST | `/api/produtos` | Adiciona um novo produto |
| PUT | `/api/produtos/{id}` | Edita um produto existente |
| DELETE | `/api/produtos/{id}` | Exclui um produto |
| POST | `/api/produtos/reajuste/{percentual}` | Reajusta preços em percentual |
| GET | `/api/movimentacoes` | Lista movimentações |
| POST | `/api/movimentacoes` | Registra nova movimentação |
| GET | `/api/relatorios/balanco` | Gera balanço físico e financeiro |
| GET | `/api/relatorios/estoque-minimo` | Lista produtos abaixo do estoque mínimo |

---

## 🧠 Testes com Postman

1. Abra o Postman.  
2. Faça uma requisição `GET` para `http://localhost:8080/api/produtos` para listar produtos.  
3. Use `POST` para criar, `PUT` para editar e `DELETE` para remover.  
4. Verifique as alterações diretamente no banco MySQL (`USE sistema_estoque; SELECT * FROM produto;`).

---

## 🗄️ Banco de Dados

- Nome do banco: `sistema_estoque`  
- O esquema é criado automaticamente pelo Hibernate ao iniciar o projeto.

---

## 🔗 Repositório

Front-end:

---

## ✅ Status do Projeto

✔️ CRUD completo para **Produto** e **Movimentação**  
⚙️ Testado com sucesso via Postman  
📦 Categoria: em desenvolvimento  

---
