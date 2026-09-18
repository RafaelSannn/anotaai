#!/bin/bash

# ==========================================
# Script de Inicialização - Modo PRODUÇÃO
# ==========================================
# Inicia a aplicação com MariaDB

set -e

echo "🚀 Iniciando Disclosed Companies em modo PRODUÇÃO (MariaDB)..."
echo ""

# Verificar se MariaDB está rodando
if ! systemctl is-active --quiet mariadb; then
    echo "⚠️  MariaDB não está rodando!"
    echo "   Execute: sudo systemctl start mariadb"
    exit 1
fi

# Verificar se o banco existe
if ! mysql -u dc_user -pdc_pass123 -e "USE disclosed_companies;" 2>/dev/null; then
    echo "❌ Banco de dados 'disclosed_companies' não encontrado!"
    echo "   Execute primeiro: sudo ./setup-mariadb.sh"
    exit 1
fi

# Exportar perfil de produção
export SPRING_PROFILES_ACTIVE=prod

# Compilar se necessário
if [ ! -d "target" ]; then
    echo "📦 Compilando projeto..."
    ./mvnw clean package -DskipTests
fi

echo "✅ Iniciando aplicação em modo PRODUÇÃO..."
echo "   Profile: prod"
echo "   Database: MariaDB (localhost:3306/disclosed_companies)"
echo "   Acesse: http://localhost:8080"
echo ""
echo "💡 Dados são PERSISTENTES - não serão perdidos ao reiniciar"
echo ""

# Iniciar aplicação
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
