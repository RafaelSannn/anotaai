# 📚 Documentação do Projeto AnotaAi

> Índice completo da documentação técnica e acadêmica

---

## 📖 Documentos Disponíveis

### 🎓 Trabalho Acadêmico
| Documento | Descrição |
|-----------|-----------|
| [`ARTIGO_CIENTIFICO.md`](ARTIGO_CIENTIFICO.md) | Artigo completo seguindo template da Revista REASE com metodologia, resultados e análise técnica |

### 🚀 Roadmap e Planejamento
| Documento | Descrição |
|-----------|-----------|
| [`ROADMAP_TECNICO.md`](ROADMAP_TECNICO.md) | Evolução v1.0→v2.0, funcionalidades implementadas, roadmap futuro (3 fases), métricas técnicas e **veredito para publicação científica** |

### 📚 Referências
| Pasta | Conteúdo |
|-------|----------|
| [`referencias-academicas/`](referencias-academicas/) | PDFs de metodologia científica, templates e guias para elaboração do artigo |

---

## 🚀 Início Rápido

### Modo Desenvolvimento (com dados demo)
```bash
./start.sh
# Acesse: http://localhost:8080
# Banco: H2 in-memory
# Login: joao@email.com / 123456
```

### Modo Produção (banco persistente)
```bash
# 1. Configurar MariaDB
sudo mariadb < setup-mariadb.sh

# 2. Inserir dados de teste
sudo mariadb < setup-dados-teste.sql

# 3. Iniciar aplicação
./start-prod.sh

# 4. Acessar
# URL: http://localhost:8080
# Login: joao@email.com / 123456
# DBeaver: localhost:3306, user: anotaai_user, senha: anotaai123
```

Consulte [`../INICIO_RAPIDO.md`](../INICIO_RAPIDO.md) para guia detalhado.

---

## 🎯 Usuários de Teste

### Visitantes
| Email | Senha |
|-------|-------|
| joao@email.com | 123456 |
| maria@email.com | 123456 |
| pedro@email.com | 123456 |

### Empresas
| Email | Senha | Categoria |
|-------|-------|-----------|
| contato@techfix.com | 123456 | Manutenção e Reparos |
| contato@cleanpro.com | 123456 | Limpeza |
| contato@belezaestilo.com | 123456 | Beleza e Estética |
| contato@automaster.com | 123456 | Automotivo |
| contato@edutech.com | 123456 | Educação |

---

## 🛠️ Tecnologias

### Backend
- Spring Boot 4.0.5
- Spring Data JPA
- Spring Security (BCrypt)
- H2 Database (dev)
- MariaDB (prod)
- Java 21

### Frontend
- HTML5 + CSS3 (CSS Variables)
- Vanilla JavaScript (ES6+)
- LocalStorage
- Dark/Light Theme System

### DevOps
- Maven 3.9+
- Bash Scripts (automação)
- JUnit 5 + Mockito (testes)

---

## 📊 Estado do Projeto

### Versão Atual: 2.0 ✅

**Status:** PRONTO PARA APRESENTAÇÃO ACADÊMICA

**Funcionalidades Implementadas:**
- ✅ Sistema de cadastro e autenticação
- ✅ Chat entre visitantes e empresas
- ✅ Publicações de serviços
- ✅ Filtros por categoria
- ✅ Sistema de tema claro/escuro
- ✅ Dual database (H2 + MariaDB)
- ✅ Dados de demonstração automáticos
- ✅ Exception handling centralizado
- ✅ Testes unitários (18 testes)
- ✅ Documentação completa

**Próximas Implementações:**
- [ ] Sistema de avaliações ⭐⭐⭐⭐⭐
- [ ] Paginação em listagens
- [ ] Validação inline de formulários
- [ ] WebSocket para chat em tempo real
- [ ] Sistema de pagamentos

Veja [`ROADMAP_TECNICO.md`](ROADMAP_TECNICO.md) para detalhes completos.

---

## 🎓 Para Publicação Científica

### Veredito: ✅ APTO PARA SUBMISSÃO

**Pontos Fortes:**
- Arquitetura sólida (MVC)
- Tecnologias atuais (Spring Boot 4.0, Java 21)
- Reprodutibilidade (instalação automatizada)
- Testes automatizados (~65% coverage)
- Documentação profissional

**Melhorias Sugeridas:**
- Adicionar diagramas UML/C4
- Métricas de performance (benchmarks)
- Testes com usuários (questionário SUS)
- Comparativo com concorrentes

**Revistas Alvo:**
- Revista REASE ⭐ (template seguido)
- RBCA - Revista Brasileira de Computação Aplicada
- SBSI - Simpósio Brasileiro de Sistemas de Informação

Veja análise completa em [`ROADMAP_TECNICO.md`](ROADMAP_TECNICO.md#-veredito-para-publicação-científica).

---

## 📞 Suporte

**Desenvolvido por:** Rafael Souza  
**Curso:** Engenharia da Computação  
**Email:** rafael.santana.sb@gmail.com  
**Ano:** 2024.2

Para dúvidas técnicas, consulte:
- [`../README.md`](../README.md) - README principal do projeto
- [`ROADMAP_TECNICO.md`](ROADMAP_TECNICO.md) - Análise técnica completa
- [`ARTIGO_CIENTIFICO.md`](ARTIGO_CIENTIFICO.md) - Documentação acadêmica
