# API de Gerenciamento de Produtos Favoritos

Este projeto consiste em uma API RESTful desenvolvida com Java e Spring Boot para gerenciar clientes e seus produtos favoritos. Ele simula o backend de uma funcionalidade comum em e-commerces e plataformas de marketplace, com integração a uma API externa pública para validação de produtos.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.3.0
- Spring Data JPA
- Spring Security + JWT
- PostgreSQL
- Docker
- OpenFeign
- Swagger / OpenAPI
- JUnit + Mockito (testes unitários)

## Funcionalidades

### Clientes
- Criar cliente (nome e e-mail obrigatórios)
- Listar clientes (com paginação e ordenação)
- Buscar cliente por ID
- Atualizar cliente (PUT e PATCH)
- Deletar cliente

**Regra:** e-mails não podem se repetir.

### Favoritos
- Adicionar produto favorito ao cliente (por ID do produto)
- Listar favoritos de um cliente (validados com a API externa)
- Remover produto favorito

**Regras:**
- Um produto não pode ser duplicado na lista de favoritos do cliente.
- Produtos são buscados e validados em tempo real pela API externa: [Fake Store API](https://fakestoreapi.com/products)

## Segurança

A aplicação utiliza autenticação via JWT. Para acessar os endpoints, é necessário realizar login e obter um token.

### Endpoints públicos:
- `/auth/login`
- Swagger: `/swagger-ui.html`
- Actuator: `/actuator/**`

### Endpoints privados (necessitam token JWT):
- `/api/clientes/**`

## Documentação

Após iniciar o projeto, acesse:

```
http://localhost:8080/swagger-ui.html
```

## Executando o Projeto com Docker

1. Certifique-se de que Docker e Docker Compose estão instalados.
2. Execute:

```bash
docker compose up --build
```

A aplicação será exposta em:
```
http://localhost:8080
```

O banco de dados PostgreSQL estará disponível em `localhost:5432`.

## Executando os Testes

Para rodar os testes unitários:
```bash
./mvnw test
```

## Postman

A collection com todos os endpoints está disponível no projeto:
```
FavoritosAPI.postman_collection.json
```

## Autor

Juan Ribeiro da Silva Pereira  
Email: juan.rsp1997@gmail.com  
LinkedIn: [https://www.linkedin.com/in/juan-ribeiro-silvapereira?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=ios_app](https://www.linkedin.com/in/juan-ribeiro-silvapereira?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=ios_app)
