@echo off
setlocal enabledelayedexpansion

REM ======= CONFIGURA QUI IL NUMERO DI ESECUZIONI ==========
set n=50

REM ======= PERCORSO BASE PER I RISULTATI ===================

for /L %%i in (1,1,%n%) do (
    echo.
    echo [ESECUZIONE %%i DI %n%]

    echo Avvio container browser...
    docker run -d -p 4444:4444 -p 7900:7900 --shm-size="2g" --name=browser selenium/standalone-chrome:127.0-chromedriver-127.0

    timeout /t 5 /nobreak >nul

    echo Avvio container Kanboard...
    docker run -d --name kanboard -p 8080:80 -t kanboard/kanboard:v1.2.15

    timeout /t 10 /nobreak >nul

    echo Esecuzione test con Maven...
    .\mvnw.cmd -Dtest=TestSuite test

    echo Salvataggio risultati...
    mkdir "..\..\..\..\..\..\ASE SI flakiness\executions\7\kanboard\conf7\%%i"
    xcopy /E /Y "target\surefire-reports\*" "..\..\..\..\..\..\ASE SI flakiness\executions\7\kanboard\conf7\%%i\"

    echo Arresto e rimozione container Docker...
    docker stop browser >nul
    docker rm browser >nul
    docker stop kanboard >nul
    docker rm kanboard >nul

    timeout /t 5 /nobreak >nul
)

echo.
echo ======= COMPLETATO =======
pause
