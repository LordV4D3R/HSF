@echo off
chcp 65001 > nul
cd /d "%~dp0"
echo ============================================================
echo   DANG DAY TOAN BO CODE & OBSIDIAN HSF302 LEN GITHUB...
echo   Remote: git@github.com:LordV4D3R/HSF.git
echo   (Vui long nhap Passphrase cua SSH Key khi duoc hoi)
echo ============================================================
echo.
git push -u origin main
echo.
echo ============================================================
echo   HOAN TAT!
echo ============================================================
pause
