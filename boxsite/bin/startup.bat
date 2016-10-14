@echo off

rem ---------------------------------------------------------------------------
rem Start script for the boxsite Server
rem ---------------------------------------------------------------------------

set BOXSITE_HOME=c:\boxsite
set BOXLIB_HOME=c:\boxlib

@echo off
setlocal enabledelayedexpansion
set BOXSITE_LIB=
for /f "delims=" %%a in ('dir /b "%BOXSITE_HOME%\WEB-INF\lib\*.jar"') do (
set "BOXSITE_LIB=!BOXSITE_LIB!;%BOXSITE_HOME%\WEB-INF\lib\%%a"
)

set JSO-COMMOM_LIB=
for /f "delims=" %%a in ('dir /b "%BOXLIB_HOME%\jso-common\lib\*.jar"') do (
set "JSO-COMMOM_LIB=!JSO-COMMOM_LIB!;%BOXLIB_HOME%\jso-commom\lib\%%a"
)
for /f "delims=" %%a in ('dir /b "%BOXLIB_HOME%\jso-common\lib\httpclient\*.jar"') do (
set "JSO-COMMOM_LIB=!JSO-COMMOM_LIB!;%BOXLIB_HOME%\jso-commom\lib\httpclient\%%a"
)
for /f "delims=" %%a in ('dir /b "%BOXLIB_HOME%\jso-common\lib\apache\*.jar"') do (
set "JSO-COMMOM_LIB=!JSO-COMMOM_LIB!;%BOXLIB_HOME%\jso-commom\lib\apache\%%a"
)
for /f "delims=" %%a in ('dir /b "%BOXLIB_HOME%\jso-common\lib\dbcp\*.jar"') do (
set "JSO-COMMOM_LIB=!JSO-COMMOM_LIB!;%BOXLIB_HOME%\jso-commom\lib\dbcp\%%a"
)
for /f "delims=" %%a in ('dir /b "%BOXLIB_HOME%\jso-common\lib\parser\*.jar"') do (
set "JSO-COMMOM_LIB=!JSO-COMMOM_LIB!;%BOXLIB_HOME%\jso-commom\lib\parser\%%a"
)
for /f "delims=" %%a in ('dir /b "%BOXLIB_HOME%\jso-common\lib\utils\*.jar"') do (
set "JSO-COMMOM_LIB=!JSO-COMMOM_LIB!;%BOXLIB_HOME%\jso-commom\lib\utils\%%a"
)

set MALLET_LIB=
for /f "delims=" %%a in ('dir /b "%BOXLIB_HOME%\mallet-2.0.6\lib\*.jar"') do (
set "MALLET_LIB=!MALLET_LIB!;%BOXLIB_HOME%\mallet-2.0.6\lib\%%a"
)

set WEBMAGIC_LIB=
for /f "delims=" %%a in ('dir /b "%BOXLIB_HOME%\webmagic\lib\*.jar"') do (
set "WEBMAGIC_LIB=!WEBMAGIC_LIB!;%BOXLIB_HOME%\webmagic\lib\%%a"
)

rem echo %JSO-COMMOM_LIB%

set "CLASSPATH=%CLASSPATH%;%BOXSITE_HOME%\WEB-INF\classes"
set "CLASSPATH=%CLASSPATH%;%BOXSITE_LIB%"
set "CLASSPATH=%CLASSPATH%;%BOXLIB_HOME%\mallet-2.0.6\class"
set "CLASSPATH=%CLASSPATH%;%MALLET_LIB%"
set "CLASSPATH=%CLASSPATH%;%BOXLIB_HOME%\webmagic\bin"
set "CLASSPATH=%CLASSPATH%;%WEBMAGIC_LIB%"
set "CLASSPATH=%CLASSPATH%;%BOXLIB_HOME%\jso-common\bin"
set "CLASSPATH=%CLASSPATH%;%JSO-COMMOM_LIB%"

set BOXSITE_MEMORY=1G
set BOXSITE_ENCODING=UTF-8

echo classpath:"%CLASSPATH%"

set CLASS=box.mgr.ProcessManager

java -Xmx%BOXSITE_MEMORY% -ea -Dfile.encoding=%BOXSITE_ENCODING% %CLASS%

