#!/usr/bin/env node
/**
 * Node.js script to generate AST JSON or code using @swc/core
 * Supports five modes: parse, transform, transform-ast, minify, print
 * 
 * Usage:
 *   node generate-ast-json.js <mode> <code> <options>
 * 
 * Arguments:
 *   mode: 'parse', 'transform', 'transform-ast', 'minify', or 'print'
 *   code: Source code as JSON string (escaped) for parse/transform/minify, or AST JSON string for print
 *   options: Options as JSON string
 * 
 * Output: AST JSON (for parse/transform-ast) or code string (for transform/minify/print)
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

            case 'transform-ast':
                // First transform the code
                const transformResult = swc.transformSync(code, options);
                // Then parse the transformed code to get AST JSON
                // Extract parser options from transform options
                const parserOptions = options.jsc?.parser || { syntax: 'ecmascript' };
                // Parse the transformed code
                const astResult = swc.parseSync(transformResult.code, parserOptions);
                // Output AST JSON of transformed code
                console.log(JSON.stringify(astResult));
                break;

            case 'minify':
                result = swc.minifySync(code, options);
                // Output minified code
                console.log(result.code);
                break;

            case 'print':
                // code is AST JSON string, parse it first
                const ast = JSON.parse(code);
                result = swc.printSync(ast, options);
                // Output generated code
                console.log(result.code);
                break;

            default:
                console.error(`Unknown mode: ${mode}. Must be 'parse', 'transform', 'transform-ast', 'minify', or 'print'`);
                process.exit(1);
        }
    } catch (error) {
        console.error('Error:', error.message);
        console.error(error.stack);
        process.exit(1);
    }
}

main();

