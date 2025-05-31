#!/bin/bash

# Super fast version using printf
OUTPUT_FILE="hello_world_10m.csv"
TOTAL_ROWS=1000000

echo "Starting super fast generation of $TOTAL_ROWS rows..."
start_time=$(date +%s)

# Remove existing file
> "$OUTPUT_FILE"

# Use printf with format string for maximum speed
printf "Hello World %d\n" $(seq 1 $TOTAL_ROWS) > "$OUTPUT_FILE"

end_time=$(date +%s)
execution_time=$((end_time - start_time))

echo "File generation completed!"
echo "Total rows: $(wc -l < "$OUTPUT_FILE")"
echo "File size: $(du -h "$OUTPUT_FILE" | cut -f1)"
echo "Execution time: $execution_time seconds"