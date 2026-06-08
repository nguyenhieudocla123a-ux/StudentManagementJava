@echo off
chcp 65001 >nul
echo ========================================
echo   TEST API SERVER
echo ========================================
echo.

REM Kiem tra curl da cai chua
where curl >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [LOI] curl chua duoc cai dat!
    echo Vui long cai dat curl hoac test bang trinh duyet
    pause
    exit /b 1
)

echo Dang test API Server tai http://localhost:8080/api
echo.

echo [1] Test GET /api/students
curl -s http://localhost:8080/api/students
echo.
echo.

echo [2] Test GET /api/teachers
curl -s http://localhost:8080/api/teachers
echo.
echo.

echo [3] Test GET /api/subjects
curl -s http://localhost:8080/api/subjects
echo.
echo.

echo [4] Test GET /api/khoa
curl -s http://localhost:8080/api/khoa
echo.
echo.

echo ========================================
echo   HOAN THANH TEST
echo ========================================
echo.
echo Neu thay du lieu JSON thi server dang chay tot!
echo.

pause
