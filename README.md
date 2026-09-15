# 🚀 AnotaAí - Plataforma de Conexão de Serviços

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

> Plataforma que conecta prestadores de serviços (empresas) com clientes (visitantes), facilitando a contratação de serviços locais.

![AnotaAí Preview](docs/preview.png)

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Instalação Rápida](#-instalação-rápida)
- [Como Usar](#-como-usar)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [API Endpoints](#-api-endpoints)
- [Testes](#-testes)
- [Contribuindo](#-contribuindo)
- [Licença](#-licença)

---

## 🎯 Sobre o Projeto

O **AnotaAí** é uma plataforma web desenvolvida como projeto acadêmico que simula um marketplace de serviços locais. A aplicação permite que:

- 👤 **Visitantes** busquem empresas, publiquem necessidades de serviços e entrem em contato via chat
- 🏢 **Empresas** criem perfis profissionais, publiquem seus serviços e recebam solicitações

### 🌟 Diferenciais

- ✅ **Interface Moderna** com tema claro/escuro
- ✅ **Chat em Tempo Real** entre usuários
- ✅ **Sistema de Publicações** para oferta e demanda de serviços
- ✅ **Filtros Avançados** por categoria e localização
- ✅ **100% Responsivo** para mobile e desktop
- ✅ **Banco de Dados H2** para demo sem configuração

---

## ⚡ Funcionalidades

### Para Visitantes
- [x] Cadastro e login
- [x] Busca de empresas por categoria
- [x] Visualização de perfis de empresas
- [x] Publicação de necessidades de serviços
- [x] Chat direto com empresas
- [x] Gerenciamento de perfil pessoal

### Para Empresas
- [x] Cadastro completo (CNPJ, categoria, localização)
- [x] Perfil profissional com descrição e imagens
- [x] Publicação de serviços oferecidos
- [x] Visualização de publicações de visitantes
- [x] Sistema de mensagens integrado
- [x] Contador de visualizações do perfil

### Recursos Técnicos
- [x] Autenticação segura com BCrypt
- [x] Upload de imagens (Base64)
- [x] Validação de formulários (frontend e backend)
- [x] Sistema de tema claro/escuro com persistência
- [x] Dados de demonstração pré-carregados

---

## 🛠 Tecnologias

### Backend
- **Spring Boot 4.0.5** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Segurança e autenticação
- **H2 Database** - Banco de dados em memória
- **Lombok** - Redução de boilerplate
- **Maven** - Gerenciamento de dependências

### Frontend
- **HTML5 / CSS3** - Estrutura e estilização
- **JavaScript (ES6)** - Lógica da aplicação
- **Vanilla JS** - Sem frameworks (simplicidade)

### Ferramentas de Desenvolvimento
- **JUnit 5** - Testes unitários
- **Mockito** - Mocks para testes
- **H2 Console** - Interface visual do banco

---

## 🚀 Instalação Rápida

### Pré-requisitos

- ☕ **Java 21+** ([Download](https://www.oracle.com/java/technologies/downloads/))
- 📦 **Maven 3.8+** (incluído via `./mvnw`)
- 🌐 **Navegador moderno** (Chrome, Firefox, Edge)
- 🗄️ **MariaDB 10.5+** (opcional, apenas para modo produção)

### Modo Desenvolvimento (com dados demo)

```bash
# 1. Clone o repositório
git clone https://github.com/seu-usuario/anotaai.git
cd anotaai

# 2. Execute o script de instalação
./install.sh

# 3. Inicie a aplicação
./start.sh
```

**Pronto!** 🎉 Acesse `http://localhost:8080` e faça login com os dados de teste.

### Modo Produção (banco persistente)

```bash
# 1. Configure o MariaDB
sudo mariadb < setup-mariadb.sh

# 2. Insira dados de teste
sudo mariadb < setup-dados-teste.sql

# 3. Inicie em modo produção
./start-prod.sh
```

Consulte [`docs/README.md`](docs/README.md) para documentação completa.

### Configuração Alternativa (IDE)

Se preferir usar uma IDE (IntelliJ IDEA, Eclipse, VS Code):

1. Importe o projeto como **Maven Project**
2. Aguarde o download das dependências
3. Execute a classe `AnotaaiApplication.java`

---

## 📖 Como Usar

### 1. Acesse a Aplicação

Abra seu navegador e acesse: **http://localhost:8080**

### 2. Faça Login com Dados de Demonstração

**Modo DEV:** Dados carregados automaticamente
**Modo PROD:** Execute `sudo mariadb < setup-dados-teste.sql` antes

#### Visitantes:
| Email | Senha |
|-------|-------|
| `joao@email.com` | `123456` |
| `maria@email.com` | `123456` |
| `pedro@email.com` | `123456` |

#### Empresas:
| Email | Senha | Categoria |
|-------|-------|-----------|
| `contato@techfix.com` | `123456` | Manutenção e Reparos |
| `contato@cleanpro.com` | `123456` | Limpeza |
| `contato@belezaestilo.com` | `123456` | Beleza e Estética |

### 3. Explore as Funcionalidades

#### Como Visitante:
1. **Explorar Empresas** → Filtrar por categoria
2. **Ver Perfil** → Clique em uma empresa
3. **Enviar Mensagem** → Inicie um chat
4. **Criar Publicação** → Divulgue sua necessidade

#### Como Empresa:
1. **Editar Perfil** → Adicione descrição e imagens
2. **Criar Publicação** → Divulgue seus serviços
3. **Ver Publicações de Visitantes** → Encontre oportunidades
4. **Responder Mensagens** → Feche negócios

### 4. Console H2 (Opcional)

Para visualizar o banco de dados:

1. Acesse: **http://localhost:8080/h2-console**
2. Configure:
   - **JDBC URL:** `jdbc:h2:mem:anotaai`
   - **Username:** `sa`
   - **Password:** _(deixe em branco)_
3. Clique em **Connect**

---

## 📁 Estrutura do Projeto

```
anotaai/
├── src/
│   ├── main/
│   │   ├── java/br/com/projeto/anotaai/
│   │   │   ├── config/           # Configurações (Security, DataInitializer)
│   │   │   ├── controller/       # Controllers REST
│   │   │   ├── dto/               # Data Transfer Objects
│   │   │   ├── model/             # Entidades JPA
│   │   │   ├── repository/        # Repositórios Spring Data
│   │   │   ├── service/           # Lógica de negócio
│   │   │   └── AnotaaiApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   │           ├── css/           # Estilos (tema claro/escuro)
│   │           ├── js/            # JavaScript modularizado
│   │           └── index.html     # Frontend SPA
│   └── test/                      # Testes unitários e integração
├── docs/                          # Documentação adicional
├── pom.xml                        # Dependências Maven
└── README.md
```

---

## 🔌 API Endpoints

### Autenticação

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/php/register.php` | Cadastro de novo usuário |
| POST | `/php/login.php` | Autenticação |

### Usuários

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/php/usuarios/empresas` | Listar todas as empresas |
| GET | `/php/usuarios/empresa/{categoria}` | Filtrar empresas por categoria |
| PUT | `/php/perfil.php` | Atualizar perfil |

### Publicações

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/publicacoes` | Listar todas as publicações |
| GET | `/api/publicacoes/usuario/{id}` | Publicações de um usuário |
| GET | `/api/publicacoes/tipo/{tipo}` | Filtrar por tipo (visitante/empresa) |
| POST | `/api/publicacoes` | Criar publicação |
| DELETE | `/api/publicacoes/{id}` | Excluir publicação |

### Mensagens

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/mensagens/usuario/{id}` | Mensagens de um usuário |
| GET | `/api/mensagens/conversa/{id1}/{id2}` | Conversa entre dois usuários |
| POST | `/api/mensagens` | Enviar mensagem |

---

## 🧪 Testes

O projeto inclui testes unitários e de integração.

```bash
# Executar todos os testes
./mvnw test

# Executar com relatório de cobertura
./mvnw test jacoco:report

# Ver relatório em: target/site/jacoco/index.html
```

### Testes Implementados

- ✅ **UsuarioServiceTest** - Lógica de cadastro e login
- ✅ **PublicacaoControllerTest** - Endpoints de publicações
- ✅ **MensagemServiceTest** - Sistema de chat
- ✅ **PrePersistTest** - Validações de entidades

---

## 🎨 Personalização

### Alterar Cores do Tema

Edite o arquivo [`src/main/resources/static/css/style2.css`](src/main/resources/static/css/style2.css:5):

```css
:root {
    --color-primary: #007bff;  /* Cor principal */
    --color-success: #28a745;  /* Cor de sucesso */
    /* ... outras variáveis */
}

[data-theme='dark'] {
    /* Variáveis para modo escuro */
}
```

### Adicionar Novas Categorias

Edite [`index.html`](src/main/resources/static/index.html:94) e adicione em ambos os selects:

```html
<option value="NovaCategoria">Nova Categoria</option>
```

### Configurar MariaDB em Produção

Edite [`application.properties`](src/main/resources/application.properties:19) e descomente:

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/anotaai
spring.datasource.username=root
spring.datasource.password=${DB_PASSWORD}
```

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Siga os passos:

1. Faça um **Fork** do projeto
2. Crie uma **branch** para sua feature (`git checkout -b feature/MinhaFeature`)
3. **Commit** suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. **Push** para a branch (`git push origin feature/MinhaFeature`)
5. Abra um **Pull Request**

### Padrões de Código

- ✅ Use **Lombok** para getters/setters
- ✅ Siga **Clean Code** (métodos pequenos, nomes descritivos)
- ✅ Adicione **testes** para novas funcionalidades
- ✅ Documente com **Javadoc** métodos públicos

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 Autor

**Rafael Souza**
Estudante de Engenharia da Computação

📧 Email: rafaelsouza@email.com
🎓 Projeto Acadêmico - 2024.2

---

## 📚 Documentação

Para mais detalhes técnicos, consulte:
- [`docs/README.md`](docs/README.md) - Índice completo da documentação
- [`docs/ARTIGO_CIENTIFICO.md`](docs/ARTIGO_CIENTIFICO.md) - Artigo acadêmico
- [`docs/ANALISE_E_MELHORIAS.md`](docs/ANALISE_E_MELHORIAS.md) - Roadmap técnico

---

## 🙏 Agradecimentos

- Spring Framework pela excelente documentação
- Comunidade open source
- Professores orientadores e colegas

---

<div align="center">

**Desenvolvido como projeto de Engenharia da Computação**
*Feito com ❤️ e ☕*

[⬆ Voltar ao topo](#-anotaái---plataforma-de-conexão-de-serviços)

</div>
