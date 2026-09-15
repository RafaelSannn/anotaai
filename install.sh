#!/bin/bash

echo "🚀 AnotaAí - Script de Instalação Automatizada"
echo "=============================================="
echo ""

# Cores para output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Verificar Java
echo -n "Verificando Java... "
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
    if [ "$JAVA_VERSION" -ge 17 ]; then
        echo -e "${GREEN}✓ Java $JAVA_VERSION encontrado${NC}"
    else
        echo -e "${RED}✗ Java 17+ necessário (encontrado: $JAVA_VERSION)${NC}"
        exit 1
    fi
else
    echo -e "${RED}✗ Java não encontrado${NC}"
    echo "Instale Java 17+: https://www.oracle.com/java/technologies/downloads/"
    exit 1
fi

# Verificar Maven
echo -n "Verificando Maven... "
if command -v mvn &> /dev/null; then
    echo -e "${GREEN}✓ Maven encontrado${NC}"
else
    echo -e "${YELLOW}⚠ Maven não encontrado, usando wrapper${NC}"
fi

# Limpar builds anteriores
echo ""
echo "🧹 Limpando builds anteriores..."
./mvnw clean

# Baixar dependências e compilar
echo ""
echo "📦 Baixando dependências e compilando..."
./mvnw install -DskipTests

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Compilação concluída com sucesso${NC}"
else
    echo -e "${RED}✗ Erro na compilação${NC}"
    exit 1
fi

# Executar testes
echo ""
echo "🧪 Executando testes..."
./mvnw test

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Todos os testes passaram${NC}"
else
    echo -e "${YELLOW}⚠ Alguns testes falharam (não é crítico)${NC}"
fi

# Iniciar aplicação
echo ""
echo "🎉 Instalação concluída!"
echo ""
echo "Para iniciar a aplicação, execute:"
echo -e "${GREEN}./mvnw spring-boot:run${NC}"
echo ""
echo "Ou use o script de início rápido:"
echo -e "${GREEN}./start.sh${NC}"
echo ""
echo "📖 Consulte o README.md para mais informações"
