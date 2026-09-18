@echo off
REM ==========================================
REM Script de Inicializacao - Modo PRODUCAO
REM ==========================================
REM Inicia a aplicacao com MariaDB (Windows)

echo.
echo Iniciando Disclosed Companies em modo PRODUCAO (MariaDB)...
echo.

REM Verificar se existe o banco
mysql -u dc_user -pdc_pass123 -e "USE disclosed_companies;" 2>nul
if %errorlevel% neq 0 (
    echo ERRO: Banco de dados 'disclosed_companies' nao encontrado!
    echo Execute primeiro os comandos SQL do guia INSTALACAO_MARIADB_WINDOWS.md
    echo.
    pause
    exit /b 1
)

REM Definir perfil de producao
set SPRING_PROFILES_ACTIVE=prod

echo OK Iniciando aplicacao em modo PRODUCAO...
echo    Profile: prod
echo    Database: MariaDB (localhost:3306/disclosed_companies)
echo    Acesse: http://localhost:8080
echo.
echo    Dados sao PERSISTENTES - nao serao perdidos ao reiniciar
echo.

REM Iniciar aplicacao
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=prod

pause
