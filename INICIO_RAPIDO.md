# 🚀 GUIA RÁPIDO: Iniciar em Modo Produção com MariaDB

> **📌 Requisito:** Java 21 ou superior instalado

## Passo 1: Configurar o Banco de Dados

Abra um terminal no VSCode (Ctrl+` ou Terminal > New Terminal) e execute:

```bash
# Entrar no MariaDB como root
sudo mariadb -u root

# Dentro do MariaDB, execute estes comandos:
CREATE DATABASE IF NOT EXISTS anotaai CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER IF NOT EXISTS 'anotaai_user'@'localhost' IDENTIFIED BY 'anotaai123';
GRANT ALL PRIVILEGES ON anotaai.* TO 'anotaai_user'@'localhost';
FLUSH PRIVILEGES;
SHOW DATABASES;
exit;
```

**✅ Pronto! Banco configurado.**

---

## Passo 2: Inserir Dados de Teste (IMPORTANTE!)

```bash
# Execute o script SQL que cria usuários de teste
sudo mariadb -u root < setup-dados-teste.sql
```

Este script cria:
- 3 visitantes (joao@email.com, maria@email.com, pedro@email.com)
- 5 empresas (contato@techfix.com, contato@cleanpro.com, etc.)
- Publicações de exemplo
- **Todos com senha: 123456**

**✅ Dados inseridos! Agora você pode fazer login.**

---

## Passo 3: Iniciar a Aplicação em Modo Produção

```bash
# No terminal do VSCode, execute:
./start-prod.sh
```

Ou manualmente:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

**⏱️ Aguarde ~10 segundos** para compilar e iniciar.

Você verá:
```
Started AnotaaiApplication in X.XXX seconds
```

---

## Passo 4: Testar se Funcionou

### Opção A: Abrir no navegador
```
http://localhost:8080
```

### Opção B: Via terminal
```bash
curl http://localhost:8080
```

---

## Passo 5: Conectar DBeaver

1. Abra o **DBeaver**
2. Clique em **Nova Conexão** (ícone de plug)
3. Selecione **MariaDB**
4. Configure:
   ```
   Host: localhost
   Porta: 3306
   Database: anotaai
   Usuário: anotaai_user
   Senha: anotaai123
   ```
5. Clique em **Test Connection** (deve aparecer "Connected")
6. Clique em **Finish**

---

## Passo 6: Usar o Sistema

### Fazer Login com Usuários de Teste:
1. Acesse http://localhost:8080
2. Use um dos emails de teste:
   - **Visitante:** joao@email.com / senha: 123456
   - **Empresa:** contato@techfix.com / senha: 123456

### Verificar no DBeaver:
```sql
-- Ver todos os usuários de teste
SELECT id, nome, email, tipo, categoria FROM usuarios;

-- Ver publicações
SELECT * FROM publicacoes;

-- Ver mensagens (quando houver)
SELECT * FROM mensagens;
```

**💡 Dica:** Cadastre novos usuários pela interface e veja-os aparecer no DBeaver!

---

## 🔄 Comandos Úteis

```bash
# Parar a aplicação (Ctrl+C no terminal)

# Reiniciar em modo PROD
./start-prod.sh

# Voltar para modo DEV (com dados demo)
./start.sh

# Ver logs do MariaDB
sudo journalctl -u mariadb -f

# Reiniciar MariaDB se necessário
sudo systemctl restart mariadb
```

---

## 🆘 Problemas Comuns

### Erro: "UnsupportedClassVersionError" ou "class file version"
```bash
# Verificar versão do Java instalada
java -version

# Se for menor que Java 21, você precisa atualizar:
# Ubuntu/Debian:
sudo apt update
sudo apt install openjdk-21-jdk

# Verificar novamente
java -version
# Deve mostrar: openjdk version "21.x.x"

# Recompilar o projeto
./mvnw clean compile
```

### Erro: "Access denied for user"
```bash
# Recriar o usuário
sudo mariadb -u root
DROP USER IF EXISTS 'anotaai_user'@'localhost';
CREATE USER 'anotaai_user'@'localhost' IDENTIFIED BY 'anotaai123';
GRANT ALL PRIVILEGES ON anotaai.* TO 'anotaai_user'@'localhost';
FLUSH PRIVILEGES;
exit;
```

### Erro: "MariaDB not running"
```bash
sudo systemctl start mariadb
sudo systemctl status mariadb
```

### Erro: "Port 8080 already in use"
```bash
# Matar processo na porta 8080
sudo lsof -t -i:8080 | xargs kill -9

# Ou reiniciar
./start-prod.sh
```

---

## ✅ Checklist

- [ ] MariaDB instalado e rodando
- [ ] Banco `anotaai` criado
- [ ] Usuário `anotaai_user` criado
- [ ] Aplicação iniciada em modo PROD
- [ ] DBeaver conectado
- [ ] Primeiro usuário cadastrado
- [ ] Dados aparecem no DBeaver

**Pronto! Agora você tem um sistema com persistência completa! 🎉**

---

## 💡 Dica: VS Code vs IntelliJ

| IntelliJ | VSCode |
|----------|--------|
| Run botão verde | `./mvnw spring-boot:run` |
| Stop botão vermelho | `Ctrl+C` no terminal |
| Console integrado | Terminal integrado (`Ctrl+\``) |
| Database tool | DBeaver externo |

O VSCode é mais leve, mas você controla tudo via terminal. É até melhor para aprender os comandos! 😉
