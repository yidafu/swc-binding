#!/usr/bin/env node
/**
 * Node.js script to generate AST JSON or code using @swc/core
 * Supports three modes: parse, transform, minify
 * 
 * Usage:
 *   node generate-ast-json.js <mode> <code> <options>
 * 
 * Arguments:
 *   mode: 'parse', 'transform', or 'minify'
 *   code: Source code as JSON string (escaped)
 *   options: Options as JSON string
 * 
 * Output: AST JSON (for parse) or code string (for transform/minify)
 */

const swc = require('@swc/core');

function main() {
    try {
        const args = process.argv.slice(2);
        
        if (args.length < 3) {
            console.error('Usage: node generate-ast-json.js <mode> <code> <options>');
            process.exit(1);
        }

        const mode = args[0];
        const code = JSON.parse(args[1]);
        const options = JSON.parse(args[2]);

        let result;

        switch (mode) {
            case 'parse':
                result = swc.parseSync(code, options);
                // Output AST JSON
                console.log(JSON.stringify(result));
                break;

            case 'transform':
                result = swc.transformSync(code, options);
                // Output transformed code
                console.log(result.code);
                break;

            case 'minify':
                result = swc.minifySync(code, options);
                // Output minified code
                console.log(result.code);
                break;

            default:
                console.error(`Unknown mode: ${mode}. Must be 'parse', 'transform', or 'minify'`);
                process.exit(1);
        }
    } catch (error) {
        console.error('Error:', error.message);
        console.error(error.stack);
        process.exit(1);
    }
}

main();

