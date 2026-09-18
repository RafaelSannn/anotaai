#!/bin/bash

echo "🚀 Iniciando Disclosed Companies..."
echo ""

# Cores
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m'

# Iniciar aplicação em background
./mvnw spring-boot:run > disclosed-companies.log 2>&1 &
PID=$!

echo "⏳ Aguardando inicialização..."
sleep 10

# Verificar se está rodando
if ps -p $PID > /dev/null; then
    echo -e "${GREEN}✓ Aplicação iniciada com sucesso!${NC}"
    echo ""
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo -e "${BLUE}🌐 Acesse: http://localhost:8080${NC}"
    echo "🗄️  Console H2: http://localhost:8080/h2-console"
    echo ""
    echo "📧 Logins de teste:"
    echo "   Visitante: joao@email.com / 123456"
    echo "   Empresa: techfix@empresa.com / 123456"
    echo ""
    echo "📋 Log: tail -f disclosed-companies.log"
    echo "🛑 Parar: ./stop.sh ou kill $PID"
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo ""
    echo "PID: $PID" > disclosed-companies.pid
else
    echo "✗ Erro ao iniciar aplicação"
    echo "Verifique disclosed-companies.log para detalhes"
    exit 1
fi
