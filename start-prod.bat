@echo off
REM ==========================================
REM Script de Inicializacao - Modo PRODUCAO
REM ==========================================
REM Inicia a aplicacao com MariaDB (Windows)

echo.
echo Iniciando AnotaAi em modo PRODUCAO (MariaDB)...
echo.

REM Verificar se existe o banco
mysql -u anotaai_user -panotaai123 -e "USE anotaai;" 2>nul
if %errorlevel% neq 0 (
    echo ERRO: Banco de dados 'anotaai' nao encontrado!
    echo Execute primeiro os comandos SQL do guia INSTALACAO_MARIADB_WINDOWS.md
    echo.
    pause
    exit /b 1
)

REM Definir perfil de producao
set SPRING_PROFILES_ACTIVE=prod

echo OK Iniciando aplicacao em modo PRODUCAO...
echo    Profile: prod
echo    Database: MariaDB (localhost:3306/anotaai)
echo    Acesse: http://localhost:8080
echo.
echo    Dados sao PERSISTENTES - nao serao perdidos ao reiniciar
echo.

REM Iniciar aplicacao
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=prod

pause
