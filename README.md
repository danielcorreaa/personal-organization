# 🚀 Personal Organization API

API REST para gerenciamento de organização pessoal com autenticação JWT.

Permite: - 🔐 Registro e login de usuários - 📂 Gerenciamento de
projetos - 🧾 Gerenciamento de itens do projeto - ⚙️ Configuração de
tipos de projeto

------------------------------------------------------------------------

# 🛠 Tecnologias Utilizadas

-   Java 17+
-   Spring Boot
-   Spring Web
-   Spring Security
-   JWT (JSON Web Token)
-   Spring Data MongoDB
-   Swagger / OpenAPI
-   Maven

------------------------------------------------------------------------

# 🔐 Autenticação

A API utiliza JWT (JSON Web Token).

## 📌 Fluxo de autenticação

1.  Usuário realiza login
2.  API retorna um token JWT
3.  Token deve ser enviado no header:

Authorization: Bearer SEU_TOKEN_AQUI

------------------------------------------------------------------------

# 🔑 Endpoints de Autenticação

Base: /api/auth

## 📝 Registrar

POST /api/auth/register

``` json
{
  "username": "daniel",
  "password": "123456"
}
```

## 🔓 Login

POST /api/auth/login

``` json
{
  "username": "daniel",
  "password": "123456"
}
```

------------------------------------------------------------------------

# 📂 Project Types

Base: /api/project-types

  Método   Endpoint         Descrição
  -------- ---------------- -------------------------
  POST     `/`              Criar tipo
  POST     `/batch`         Criar/atualizar em lote
  PUT      `/{id}`          Atualizar
  PATCH    `/{id}/active`   Ativar/Desativar
  GET      `/{id}`          Buscar por ID
  GET      `/`              Listar todos
  DELETE   `/{id}`          Remover

------------------------------------------------------------------------

# 📂 Projects

Base: /api/projects

🔒 Requer autenticação.

  Método   Endpoint                Descrição
  -------- ----------------------- -------------------
  POST     `/`                     Criar projeto
  GET      `/{id}`                 Buscar por ID
  GET      `/?status=ACTIVE`       Listar por status
  PUT      `/{id}`                 Atualizar
  PATCH    `/{projectId}/status`   Atualizar status
  DELETE   `/{id}`                 Remover

------------------------------------------------------------------------

# 📂 Project Items

Base: /api/projects/items

🔒 Requer autenticação.

  Método   Endpoint                Descrição
  -------- ----------------------- ---------------------
  POST     `/{projectId}`          Criar item
  GET      `/{projectId}`          Listar itens
  DELETE   `/{itemId}`             Remover item
  PATCH    `/{itemId}/price`       Atualizar preço
  PATCH    `/{itemId}/completed`   Atualizar completed

------------------------------------------------------------------------

# 🧱 Arquitetura

Estrutura baseada em camadas:

domain\
infrastructure\
├── mongo\
├── security\
├── web\
├── api\
├── dto\
├── mapper\
└── service

------------------------------------------------------------------------

# 📖 Swagger

Disponível em: http://localhost:8080/swagger-ui/index.html

------------------------------------------------------------------------

# ▶️ Como Executar

## Clonar

git clone https://github.com/seu-usuario/personal-organization.git

## Rodar

mvn spring-boot:run

ou

mvn clean package\
java -jar target/\*.jar

------------------------------------------------------------------------

# 📌 Status Codes

  Código   Descrição
  -------- --------------
  200      OK
  201      Created
  204      No Content
  400      Bad Request
  401      Unauthorized
  404      Not Found

------------------------------------------------------------------------

# 📜 Licença

Projeto desenvolvido para fins educacionais e organização pessoal.
