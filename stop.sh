#!/bin/bash

echo "🛑 Parando Disclosed Companies..."

if [ -f disclosed-companies.pid ]; then
    PID=$(cat disclosed-companies.pid)
    if ps -p $PID > /dev/null; then
        kill $PID
        echo "✓ Aplicação parada (PID: $PID)"
        rm disclosed-companies.pid
    else
        echo "✗ Processo não encontrado"
        rm disclosed-companies.pid
    fi
else
    echo "✗ PID file not found"
    echo "Tentando encontrar processo..."
    pkill -f "spring-boot:run"
    echo "✓ Processo Spring Boot finalizado"
fi
