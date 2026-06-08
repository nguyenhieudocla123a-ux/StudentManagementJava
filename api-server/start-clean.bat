@echo off
echo ========================================
echo   RESTART API SERVER (CLEAN BUILD)
echo ========================================
echo.

echo [1/2] Clean va compile...
mvn clean compile
if errorlevel 1 (
    echo.
    echo ========================================
    echo   LOI COMPILE - XEM CHI TIET O TREN
    echo ========================================
    pause
    exit /b 1
)

echo.
echo [2/2] Khoi dong API Server...
echo Port: 8080
echo.

mvn spring-boot:run

echo.
echo ========================================
echo   SERVER STOPPED
echo ========================================
pause
