#!/bin/bash

echo "Converting text files to markdown using Pandoc..."
echo

# Find all .tex files recursively and convert them
find . -type f -name "*.tex" | while read -r input; do
    # Get the output filename by replacing .tex with .md
    output="${input%.tex}.md"
    
    echo "Converting: $input"
    echo "To: $output"
    
    pandoc "$input" -t markdown -o "$output" 
    # --from=latex+raw_tex
    
    if [ $? -eq 0 ]; then
        echo "Success: $output"
    else
        echo "Error converting: $input"
    fi
    echo
done

echo
echo "Conversion complete!"