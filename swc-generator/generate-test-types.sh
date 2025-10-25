#!/bin/bash

# Batch generate types.kt files for test files swc-01 through swc-27
# Output structure: tests/swc-XX/types.kt

set -e  # Exit on error

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

cd "$PROJECT_ROOT"

echo "=========================================="
echo "SWC Generator - Batch Test Types Generator"
echo "=========================================="
echo ""

# Test files directory
TESTS_DIR="$PROJECT_ROOT/swc-generator/tests"
OUTPUT_BASE_DIR="$PROJECT_ROOT/swc-generator/tests"

# Counter for summary
success_count=0
error_count=0
skipped_count=0

# Get all test files (both in root and in subdirectories) and sort them
TEST_FILES=($(find "$TESTS_DIR" -name "swc-*.d.ts" | sort -V))

echo "Found ${#TEST_FILES[@]} test files to process"
echo ""

# Process each test file
for test_file in "${TEST_FILES[@]}"; do
    # Extract filename without path
    filename=$(basename "$test_file")
    
    # Extract test identifier (e.g., "swc-01" from "swc-01-base-types.d.ts")
    # Handle cases like "swc-03-module-types.d.ts" -> "swc-03-module"
    if [[ $filename =~ ^(swc-[0-9]+)-(.*)\.d\.ts$ ]]; then
        test_id="${BASH_REMATCH[1]}"
        suffix="${BASH_REMATCH[2]}"
        
        # Use full identifier including suffix for subdirectory name
        # e.g., "swc-03-module" not just "swc-03"
        subdir_name="${test_id}-${suffix}"
        
        # Create output directory
        output_dir="$OUTPUT_BASE_DIR/$subdir_name"
        output_file="$output_dir/types.kt"
        
        # Create output directory
        mkdir -p "$output_dir"
        
        echo "Processing: $filename"
        echo "  Input:  $test_file"
        echo "  Output: $output_file"
        
        # Run the generator
        if ./gradlew :swc-generator:run --args="-i $test_file" --quiet > /dev/null 2>&1; then
            # Move the generated types.kt to the test subdirectory
            # The default output path is in swc-binding/src/main/kotlin/dev/yidafu/swc/generated/types.kt
            default_output="$PROJECT_ROOT/swc-binding/src/main/kotlin/dev/yidafu/swc/generated/types.kt"
            
            if [ -f "$default_output" ]; then
                # Move the generated types.kt to the test subdirectory
                mv "$default_output" "$output_file"
                
                # Move the .d.ts file to the test subdirectory as well (only if it's not already there)
                if [ "$(dirname "$test_file")" != "$output_dir" ]; then
                    mv "$test_file" "$output_dir/"
                fi
                
                echo "  ✓ Success"
                ((success_count++))
            else
                echo "  ✗ Error: Generated file not found at $default_output"
                ((error_count++))
            fi
        else
            echo "  ✗ Error: Generation failed"
            ((error_count++))
        fi
        
        echo ""
    else
        echo "Warning: Could not parse filename: $filename"
        ((skipped_count++))
    fi
done

# Summary
echo "=========================================="
echo "Summary"
echo "=========================================="
echo "Success:  $success_count"
echo "Errors:   $error_count"
echo "Skipped:  $skipped_count"
echo "Total:    ${#TEST_FILES[@]}"
echo "=========================================="

# Exit with error if any failures occurred
if [ $error_count -gt 0 ]; then
    exit 1
fi
