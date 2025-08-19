# ForumHub API

API REST para um fórum de tópicos no estilo Alura, construída em **Spring Boot**, com autenticação via **JWT** e persistência em **MySQL**.

---

## ⚙️ Tecnologias e Stack

- Java 17+ (ou 21, se preferir)
- Spring Boot 3.1.4
- Spring Web
- Spring Data JPA
- Spring Security + JWT
- Bean Validation
- MySQL
- Flyway (migrations)
- Lombok (opcional)

---

## 🚀 Como rodar o projeto

### 1. Clonar o repositório

  ```bash
  git clone https://github.com/seu-usuario/forumhub.git
  cd forumhub
  ```
### 2. Configurar o MySQL

Crie um banco de dados chamado `forumhub`:

  ```sql
  CREATE DATABASE forumhub;
  ```
### 3. No arquivo `src/main/resources/application.properties`, configure suas credenciais:

  ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/forumhub
  spring.datasource.username=seu_usuario
  spring.datasource.password=sua_senha
  spring.jpa.hibernate.ddl-auto=validate
  spring.flyway.enabled=true
  
  security.jwt.secret=MinhaChaveSecretaSuperSegura1234567890
  security.jwt.expiration=86400000
  ```
### 4. Execute a aplicação com o seguinte comando, estando no diretório que contém o pom.xml

  ```bash
  mvn spring-boot:run
  ```
## Endpoints

## 🔹 Autenticação

### Registrar usuário
**POST** `/auth/register`  
**Body de exemplo:**

  ```json
  {
    "nome": "João Silva",
    "email": "joao@email.com",
    "senha": "123456"
  }
  ```
Retorna: 201 Created

Sem token necessário

### Login
**POST** `/auth/login`  
**Body de exemplo:**

  ```json
  {
    "email": "joao@email.com",
    "senha": "123456"
  }
  ```
Retorna: 200 OK + JWT

Sem token necessário

## 🔹 Tópicos

### Listar todos
**GET** `/topicos`  
- Parâmetros opcionais: `page`, `size`, `curso`  
- Retorna: lista paginada de tópicos

### Buscar por ID
**GET** `/topicos/{id}`  
- Retorna: `200 OK` ou `404 Not Found`

### Criar tópico
**POST** `/topicos`  
**Header:** `Authorization: Bearer <token>`  
**Body de exemplo:**

  ```json
  {
    "titulo": "Problema com JPA",
    "mensagem": "Não consigo mapear entidade",
    "nomeCurso": "Spring Boot"
  }
  ```
Retorna: 201 Created + Location

### Atualizar tópico
**PUT** `/topicos/{id}`  
**Header:** `Authorization: Bearer <token>`  
**Body de exemplo:** (titulo e/ou mensagem)

  ```json
  {
    "titulo": "Problema resolvido",
    "mensagem": "Usei CascadeType.ALL"
  }
  ```
Retorna: 200 OK

Só o autor pode atualizar

### Excluir tópico
**DELETE** `/topicos/{id}`  
**Header:** `Authorization: Bearer <token>`  

- Retorna: `204 No Content`  
- Só o autor pode deletar
