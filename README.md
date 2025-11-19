# 📦 Sistema de Estoque – Back-End

## 🧑‍💻 Integrantes do Grupo
- Lucas da Costa  
- Carlos Henrique Lohn  
- João Víctor Muniz da Silva  
- Rennan Rosa Guedes  
- André Leonardo da Silva  

---

# 📖 Descrição do Projeto

O **Sistema de Estoque** é a aplicação Back-End desenvolvida para a A3 da unidade curricular **Sistemas Distribuídos e Mobile – UNISUL**, implementada em **Java + Spring Boot** com banco de dados **MySQL**, seguindo padrões de projeto e arquitetura em camadas.

O sistema permite:

- Cadastro, edição, listagem e exclusão de produtos  
- Cadastro e gerenciamento de categorias  
- Registro de movimentações (entrada/saída)  
- Reajuste percentual de preços  
- Geração de relatórios administrativos  
- Controle automático do estoque, com avisos quando o nível mínimo/máximo é ultrapassado  

A aplicação opera como serviço remoto, sendo consumida por uma aplicação Front-End hospedada em outra máquina, caracterizando **arquitetura distribuída**.

---

# ⚙️ Tecnologias Utilizadas

| Categoria | Tecnologias |
|---|---|
| Linguagem | Java 17 |
| Framework | Spring Boot 3.3.1 |
| Padrão REST | Spring Web |
| Persistência | Spring Data JPA / Hibernate |
| Banco de Dados | MySQL |
| Validações | Jakarta Validation |
| ORM | Hibernate |
| Build | Maven |
| IDE | IntelliJ IDEA Ultimate |
| Testes manuais | Postman |

---

# 🧩 Padrões de Projeto Aplicados

- **MVC (Model–View–Controller)**  
  Separação clara entre camadas de Controller, Service e Repository.

- **Service Layer Pattern**  
  Toda regra de negócio centralizada na camada `service`.

- **DTO Pattern**  
  Objetos de transferência utilizados para requisições e respostas.

- **Repository Pattern**  
  Uso de `JpaRepository` para persistência.

---

# 🧱 Arquitetura da Aplicação

┌───────────────┐ ┌──────────────┐ ┌────────────┐ ┌──────────────┐
│ Controller │ → │ Service │ → │ Repository │ → │ Database │
└───────────────┘ └──────────────┘ └────────────┘ └──────────────┘
│
▼
[DTO ↔ Entity]


---

# 📁 Estrutura do Projeto

src/
└── main/
├── java/
│ └── com/sistema/estoque/
│ ├── controller/
│ │ ├── ProdutoController.java
│ │ ├── MovimentacaoController.java
│ │ ├── CategoriaController.java
│ │ └── RelatorioController.java
│ │
│ ├── dto/
│ │ ├── ProdutoDTO.java
│ │ ├── MovimentacaoDTO.java
│ │ ├── CategoriaCreateDTO.java
│ │ └── CategoriaResponseDTO.java
│ │
│ ├── entity/
│ │ ├── Produto.java
│ │ ├── Movimentacao.java
│ │ ├── TipoMovimentacao.java
│ │ └── Categoria.java
│ │
│ ├── exception/
│ │ ├── BusinessException.java
│ │ └── ResourceNotFoundException.java
│ │
│ ├── repository/
│ │ ├── ProdutoRepository.java
│ │ ├── MovimentacaoRepository.java
│ │ └── CategoriaRepository.java
│ │
│ ├── service/
│ │ ├── ProdutoService.java
│ │ ├── MovimentacaoService.java
│ │ └── CategoriaService.java
│ │
│ └── SistemaDeEstoqueApplication.java
│
└── resources/
├── static/
├── templates/
└── application.properties

test/
└── java/com/sistema/estoque/
└── SistemaDeEstoqueApplicationTests.java


---

# 📦 Dependências (pom.xml)

Principais bibliotecas utilizadas:

```xml
spring-boot-starter-web
spring-boot-starter-data-jpa
spring-boot-starter-validation
mysql-connector-j
lombok
```
Java 17 e Spring Boot 3.3.1.

---

#🚀 Execução do Projeto
##1️⃣ Clonar o repositório

git clone https://github.com/seu-usuario/estoque-backend.git
cd estoque-backend

##2️⃣ Criar o banco de dados MySQL

CREATE DATABASE sistema_estoque;

##3️⃣ Configurar o application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/sistema_estoque
spring.datasource.username=root
spring.datasource.password=senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

##4️⃣ Executar

Terminal: mvn spring-boot:run

Ou pela IDE executando:SistemaDeEstoqueApplication

Servidor será iniciado em:http://localhost:8080

# 🔌 Endpoints REST Disponíveis

## 📍 Produtos (`/api/produtos`)
| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/produtos` | Lista produtos |
| POST | `/api/produtos` | Cria produto |
| PUT | `/api/produtos/{id}` | Edita produto |
| DELETE | `/api/produtos/{id}` | Exclui |
| POST | `/api/produtos/reajuste/{percentual}` | Reajuste de preços |

---

## 📍 Categorias (`/api/categorias`)
| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/categorias` | Lista categorias |
| POST | `/api/categorias` | Cria categoria |
| PUT | `/api/categorias/{id}` | Edita categoria |
| DELETE | `/api/categorias/{id}` | Remove |

---

## 📍 Movimentações (`/api/movimentacoes`)
| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/movimentacoes` | Lista movimentações |
| POST | `/api/movimentacoes` | Registra entrada/saída |

### Regras de negócio aplicadas:

- **Entrada** → aumenta estoque  
- **Saída** → reduz estoque  
- Validação automática de limites mínimos e máximos  

---

## 📊 Relatórios (`/api/relatorios`)
| Endpoint | Descrição |
|---|---|
| `/api/relatorios/lista-precos` | Lista de preços por produto |
| `/api/relatorios/balanco` | Balanço físico e financeiro |
| `/api/relatorios/estoque-minimo` | Produtos abaixo do mínimo |
| `/api/relatorios/por-categoria` | Quantidade por categoria |
| `/api/relatorios/maior-movimentacao` | Produtos mais movimentados |

---

# 🧪 Testes com Postman

1. Abrir o Postman  
2. Executar requisições como:

GET http://localhost:8080/api/produtos

3. Criar, editar ou excluir itens via:

POST / PUT / DELETE

4. Verificar os dados no MySQL:

```sql
USE sistema_estoque;
SELECT * FROM produto;
```
# 🧠 Regras de Negócio Implementadas

- Nenhum campo de preço ou quantidade pode ser negativo  
- Quantidade mínima não pode ser maior que a máxima  
- Movimentações ajustam o estoque automaticamente  
- Retorno de erro apropriado quando o recurso não existe  
- Alertas gerados quando:
  - Estoque fica **abaixo do mínimo**
  - Estoque fica **acima do máximo**

---

# 🧭 Status do Projeto

| Funcionalidade | Status |
|---|---|
| CRUD de Produto | ✔ Concluído |
| CRUD de Categoria | ✔ Concluído |
| CRUD de Movimentação | ✔ Concluído |
| DTOs e Validações | ✔ Implementados |
| Relatórios | ✔ Funcionando |
| Comunicação distribuída | ✔ Via REST |
| Consumo por Front-End separado | ✔ Entregue |

---

# 🔗 Repositórios

| Parte | Link |
|---|---|
| Back-End | _inserir link_ |
| Front-End | _inserir link_ |

---

# 📜 Licença

Projeto distribuído sob licença **MIT**, livre para estudo e evolução.

---

# 🏁 Conclusão

Este back-end atende a todos os requisitos da A3:

- CRUD completo  
- Regras avançadas de estoque  
- Relatórios gerenciais  
- Arquitetura distribuída  
- Projeto versionado colaborativamente  
- Estrutura profissional em camadas  
