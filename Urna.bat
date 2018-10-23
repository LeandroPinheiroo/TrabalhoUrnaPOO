@echo off
if exist "./Urna.jar" goto programaEncontrado
echo Nao foi possivel encontrar o programa especificado
pause
goto fim

:programaEncontrado

java -jar ./Urna.jar

:fim
