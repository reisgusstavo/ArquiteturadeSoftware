@echo off
cd /d "%~dp0.."
if not exist out mkdir out
dir /s /b src\*.java > fontes.txt
javac -encoding UTF-8 -cp lib\conn.jar -d out @fontes.txt
del fontes.txt
