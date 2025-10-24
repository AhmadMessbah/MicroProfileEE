@echo off
echo ========================================
echo    MFT Plus - Microservices System
echo ========================================
echo.

REM Set TomEE Home
set TOMEE_HOME=C:\root\apache-tomee-microprofile-10.1.2

REM Set Java Home (adjust if needed)
set JAVA_HOME=%JAVA_HOME%

REM Set project directory
set PROJECT_DIR=%~dp0

echo [INFO] TomEE Home: %TOMEE_HOME%
echo [INFO] Project Directory: %PROJECT_DIR%
echo.

REM Check if TomEE exists
if not exist "%TOMEE_HOME%" (
    echo [ERROR] TomEE not found at: %TOMEE_HOME%
    echo [ERROR] Please update TOMEE_HOME in this script
    pause
    exit /b 1
)

REM Check if Java exists
java -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Java not found. Please install Java 17+
    pause
    exit /b 1
)

echo [INFO] Building project...
cd /d "%PROJECT_DIR%"
call mvn clean package -q
if errorlevel 1 (
    echo [ERROR] Build failed
    pause
    exit /b 1
)

echo [INFO] Build successful
echo.

REM Copy WAR file to TomEE
echo [INFO] Deploying to TomEE...
copy "%PROJECT_DIR%target\mftplus-1.0-SNAPSHOT.war" "%TOMEE_HOME%\webapps\" >nul
if errorlevel 1 (
    echo [ERROR] Failed to copy WAR file
    pause
    exit /b 1
)

REM Copy tomee.xml to TomEE conf
echo [INFO] Copying configuration...
copy "%PROJECT_DIR%src\main\resources\tomee.xml" "%TOMEE_HOME%\conf\" >nul
if errorlevel 1 (
    echo [WARNING] Failed to copy tomee.xml, continuing anyway...
)

echo [INFO] Starting TomEE Server...
echo [INFO] Server will be available at: http://localhost:8080/mftplus/
echo [INFO] Press Ctrl+C to stop the server
echo.

REM Start TomEE
cd /d "%TOMEE_HOME%\bin"
call catalina.bat run

echo.
echo [INFO] TomEE Server stopped
pause
