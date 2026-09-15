#!/bin/bash

# ==========================================
# Script de Instalação do MariaDB - AnotaAi
# ==========================================
# Instala e configura MariaDB para produção
# Compatível com Ubuntu/Debian e derivados

set -e

echo "🗄️  Instalando MariaDB para AnotaAi..."
echo ""

# Verificar se é root
if [ "$EUID" -ne 0 ]; then 
    echo "❌ Este script precisa ser executado como root (use sudo)"
    exit 1
fi

# Detectar sistema operacional
if [ -f /etc/os-release ]; then
    . /etc/os-release
    OS=$ID
else
    echo "❌ Sistema operacional não identificado"
    exit 1
fi

# Instalar MariaDB
echo "📦 Instalando MariaDB Server..."
if [ "$OS" = "ubuntu" ] || [ "$OS" = "debian" ]; then
    apt-get update
    apt-get install -y mariadb-server mariadb-client
elif [ "$OS" = "fedora" ] || [ "$OS" = "rhel" ] || [ "$OS" = "centos" ]; then
    dnf install -y mariadb-server mariadb
else
    echo "⚠️  Sistema $OS não testado, tentando instalação genérica..."
    apt-get update && apt-get install -y mariadb-server mariadb-client || \
    dnf install -y mariadb-server mariadb
fi

# Iniciar serviço
echo "🚀 Iniciando serviço MariaDB..."
systemctl start mariadb
systemctl enable mariadb

# Aguardar inicialização
sleep 3

# Criar banco e usuário
echo "🔧 Configurando banco de dados e usuário..."
mariadb -u root <<-EOF
-- Criar database
CREATE DATABASE IF NOT EXISTS anotaai CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Criar usuário
CREATE USER IF NOT EXISTS 'anotaai_user'@'localhost' IDENTIFIED BY 'anotaai123';

-- Conceder permissões
GRANT ALL PRIVILEGES ON anotaai.* TO 'anotaai_user'@'localhost';
FLUSH PRIVILEGES;

-- Mostrar databases
SHOW DATABASES;
EOF

echo ""
echo "✅ MariaDB instalado e configurado com sucesso!"
echo ""
echo "📊 Informações de Conexão:"
echo "   Host: localhost"
echo "   Porta: 3306"
echo "   Database: anotaai"
echo "   Usuário: anotaai_user"
echo "   Senha: anotaai123"
echo ""
echo "🔧 Para gerenciar via DBeaver:"
echo "   1. Abra o DBeaver"
echo "   2. Nova Conexão > MariaDB"
echo "   3. Use as credenciais acima"
echo ""
echo "⚙️  Para usar em produção, execute:"
echo "   ./start-prod.sh"
echo ""
