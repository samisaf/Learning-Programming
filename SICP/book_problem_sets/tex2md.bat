@echo off
setlocal enabledelayedexpansion

echo Converting text files to markdown using Pandoc...
echo.

REM Loop through all .tex files in current directory and subdirectories
for /r %%f in (*.tex) do (
    set "input=%%f"
    set "output=%%~dpnf.md"
    
    echo Converting: %%f
    echo To: !output!
    
    pandoc "!input!" -t markdown -o "!output!" 
    @REM --from=latex+raw_tex
    
    if !errorlevel! equ 0 (
        echo Success: !output!
    ) else (
        echo Error converting: %%f
    )
    echo.
)

echo.
echo Conversion complete!
pause