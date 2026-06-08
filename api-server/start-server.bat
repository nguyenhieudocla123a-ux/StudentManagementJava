@echo off
setlocal enabledelayedexpansion

echo ========================================
echo   KHOI DONG API SERVER
echo ========================================
echo.
echo Dang khoi dong server...
echo Server se chay tai: http://localhost:8080/api
echo.
echo Nhan Ctrl+C de dung server
echo ========================================
echo.

cd /d "%~dp0"

REM ===== JAVA 17 =====
set "JDK17_PATH="

if exist "E:\LTJava\jdk-17\bin\java.exe" set "JDK17_PATH=E:\LTJava\jdk-17"
if exist "E:\LTjava\jdk-17\bin\java.exe" if not defined JDK17_PATH set "JDK17_PATH=E:\LTjava\jdk-17"
if exist "C:\Program Files\Java\jdk-17\bin\java.exe" if not defined JDK17_PATH set "JDK17_PATH=C:\Program Files\Java\jdk-17"

if defined JDK17_PATH (
    set "JAVA_HOME=!JDK17_PATH!"
    echo [OK] Su dung JDK 17: !JAVA_HOME!
) else if "%JAVA_HOME%"=="" (
    echo [LOI] Khong tim thay JDK 17!
    echo Vui long cai JDK 17 va chay lai.
    pause
    exit /b 1
) else (
    echo [OK] JAVA_HOME: %JAVA_HOME%
)

set "PATH=%JAVA_HOME%\bin;%PATH%"

echo.
echo Kiem tra phien ban Java:
java -version 2>&1
echo.

REM ===== MAVEN =====
set "MVN_CMD="

REM 1. Maven trong PATH
where mvn >nul 2>nul
if %ERRORLEVEL% EQU 0 set "MVN_CMD=mvn"

REM 2. Maven cai trong E:\LTjava
if not defined MVN_CMD if exist "E:\LTjava\apache-maven-3.9.12-bin\apache-maven-3.9.12\bin\mvn.cmd" (
    set "MVN_CMD=E:\LTjava\apache-maven-3.9.12-bin\apache-maven-3.9.12\bin\mvn.cmd"
)
if not defined MVN_CMD if exist "E:\LTJava\apache-maven-3.9.12-bin\apache-maven-3.9.12\bin\mvn.cmd" (
    set "MVN_CMD=E:\LTJava\apache-maven-3.9.12-bin\apache-maven-3.9.12\bin\mvn.cmd"
)

REM 3. Tim bat ky thu muc apache-maven trong E:\LTjava hoac E:\LTJava
if not defined MVN_CMD (
    for /d %%D in ("E:\LTjava\apache-maven-*" "E:\LTJava\apache-maven-*") do (
        if exist "%%D\bin\mvn.cmd" set "MVN_CMD=%%D\bin\mvn.cmd"
    )
)

REM 4. Maven di kem IntelliJ IDEA
if not defined MVN_CMD (
    for /d %%D in ("C:\Program Files\JetBrains\IntelliJ IDEA*") do (
        if exist "%%D\plugins\maven\lib\maven3\bin\mvn.cmd" set "MVN_CMD=%%D\plugins\maven\lib\maven3\bin\mvn.cmd"
    )
)

REM 5. Maven trong Program Files
if not defined MVN_CMD if exist "C:\Program Files\Apache\maven\bin\mvn.cmd" (
    set "MVN_CMD=C:\Program Files\Apache\maven\bin\mvn.cmd"
)

if not defined MVN_CMD (
    echo [LOI] Khong tim thay Maven!
    echo.
    echo Da tim trong:
    echo   - PATH he thong
    echo   - E:\LTjava\apache-maven-*
    echo   - IntelliJ IDEA (plugins\maven)
    echo.
    echo Cach khac phuc:
    echo   1. Them Maven vao PATH, hoac
    echo   2. Giai nen Maven vao E:\LTjava\apache-maven-3.9.12-bin
    echo.
    pause
    exit /b 1
)

echo [OK] Su dung Maven: !MVN_CMD!
echo.
echo Dang build va chay server...
echo.

call "!MVN_CMD!" clean compile spring-boot:run
if errorlevel 1 (
    echo.
    echo [LOI] Build that bai!
    echo Neu gap loi "cannot find symbol getTenDangNhap/getLoaiNguoiDung":
    echo   - Dam bao dang dung JDK 17 (khong phai JDK 24)
    echo.
)

pause
endlocal
