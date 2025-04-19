# 🛠️ Sistema de Gerenciamento de Ordens de Serviço (OS)

Sistema completo para gerenciamento de ordens de serviço com autenticação JWT e controle de acesso baseado em roles.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.x**
- **Spring Security 6.x**
- **JWT (JSON Web Tokens)**
- **Hibernate / JPA**
- **Lombok**
- **Maven**
- **MySQL 8.0**

---

## 📋 Índice

- [✨ Funcionalidades](#✨-funcionalidades)
- [🛠️ Tecnologias](#🛠️-tecnologias)
- [📊 Modelo de Dados](#📊-modelo-de-dados)
- [🔌 Endpoints](#🔌-endpoints)
- [⚙️ Configuração](#⚙️-configuração)
- [💡 Exemplos de Uso](#💡-exemplos-de-uso)
- [🔒 Segurança](#🔒-segurança)
- [📜 Licença](#📜-licença)

---

## ✨ Funcionalidades

### 🔐 Autenticação e Usuários

- ✅ Cadastro de clientes (usuários)
- ✅ Login com gerenciamento de sessão JWT
- 🔐 Dois níveis de acesso: `ROLE_USER` e `ROLE_ADMIN`
- 🔒 Encriptação de senhas com **BCrypt**

### 📦 Ordens de Serviço

- 🆕 Criação de OS com múltiplos campos
- 📋 Listagem paginada de OS
- ✏️ Atualização de OS existentes
- 🗑️ Exclusão lógica de OS
- 👮 Controle de acesso granular:
  - 👤 `USER`: acesso apenas às próprias OS
  - 👑 `ADMIN`: acesso completo a todas as OS

---

## 🛠️ Tecnologias

### Backend

- Java 17
- Spring Boot 3.x
- Spring Security 6.x
- JWT (JSON Web Tokens)
- Hibernate / JPA
- Lombok
- Maven

### Banco de Dados

- MySQL 8.0

---

## 📊 Modelo de Dados

### 🧑‍💼 Entidade `Cliente`

| Campo  | Tipo  | Descrição                      |
|--------|-------|-------------------------------|
| id     | Long  | Identificador único            |
| nome   | String| Nome completo                  |
| login  | String| Email (único)                  |
| senha  | String| Senha encriptada               |
| role   | Enum  | `ROLE_USER` ou `ROLE_ADMIN`    |

### 📄 Entidade `ServiceOrder`

| Campo                     | Tipo            | Descrição                                   |
|--------------------------|------------------|---------------------------------------------|
| id                       | Long             | Identificador único                         |
| destinacaoOrdemDeServico| Enum             | `EVENTO`, `ARTE`, `VIDEO`                   |
| detalhamento             | String           | Descrição detalhada                         |
| dataCriacao              | LocalDateTime    | Data de criação                             |
| ultimaAtualizacao        | LocalDateTime    | Data da última atualização                  |
| localDoEvento            | String           | Local do evento                             |
| horarioDoEvento          | String           | Horário do evento                           |
| status                   | Enum             | `ABERTA`, `FECHADA`, `PENDENTE`, `CONCLUÍDA`|
| prioridade               | Enum             | `BAIXA`, `MEDIA`, `ALTA`                    |
| cliente                  | Cliente          | Relacionamento com o cliente                |
| ativo                    | Boolean          | Flag para exclusão lógica                   |

---

## 🔌 Endpoints

### 🔐 Autenticação

| Método | Endpoint            | Descrição                          |
|--------|---------------------|------------------------------------|
| POST   | `/cliente/login`    | Autentica usuário e retorna token  |
| POST   | `/cliente/cadastro` | Cadastra novo usuário              |

### 📝 Ordens de Serviço

| Método | Endpoint         | Descrição                              |
|--------|------------------|----------------------------------------|
| POST   | `/order`         | Cria nova OS                           |
| GET    | `/order`         | Lista OS (paginado)                    |
| GET    | `/order/{id}`    | Detalhes de uma OS específica          |
| PUT    | `/order/{id}`    | Atualiza uma OS existente              |
| DELETE | `/order/{id}`    | Remove uma OS (exclusão lógica)        |

---

## ⚙️ Configuração

### Pré-requisitos

- Java 17 JDK
- MySQL 8.0+
- Maven 3.8+

### Configuração do Banco de Dados

```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/serviceorder_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### Variáveis de Ambiente

Configure o secret para JWT:

```properties
api.security.token.secret=seu_secret_aqui
```

### Executando a Aplicação

```bash
mvn spring-boot:run
```

---

## 💡 Exemplos de Uso

### 📌 Cadastro de Usuário

```http
POST /cliente/cadastro
Content-Type: application/json

{
  "nome": "Maria Silva",
  "login": "maria@empresa.com",
  "senha": "senhaSegura123",
  "role": "ROLE_ADMIN"
}
```

### 🔑 Login

```http
POST /cliente/login
Content-Type: application/json

{
  "login": "maria@empresa.com",
  "senha": "senhaSegura123"
}
```

**Resposta:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### 🆕 Criação de OS

```http
POST /order
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
Content-Type: application/json

{
  "destinacaoOrdemDeServico": "EVENTO",
  "detalhamento": "Festa de formatura",
  "localDoEvento": "Centro de Convenções",
  "horarioDoEvento": "19:00"
}
```

---

## 🔒 Segurança

- Autenticação via JWT com expiração de **2 horas**
- Autorização baseada em **roles**
- CSRF **desabilitado** (stateless)
- Sessão **STATELESS**
- Senhas encriptadas com **BCrypt**
- Headers obrigatórios:

```
Authorization: Bearer <token>
```

---
