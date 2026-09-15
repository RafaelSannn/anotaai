#!/bin/bash

echo "🛑 Parando AnotaAí..."

if [ -f anotaai.pid ]; then
    PID=$(cat anotaai.pid)
    if ps -p $PID > /dev/null; then
        kill $PID
        echo "✓ Aplicação parada (PID: $PID)"
        rm anotaai.pid
    else
        echo "✗ Processo não encontrado"
        rm anotaai.pid
    fi
else
    echo "✗ PID file not found"
    echo "Tentando encontrar processo..."
    pkill -f "spring-boot:run"
    echo "✓ Processo Spring Boot finalizado"
fi
