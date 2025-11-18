package dev.yidafu.swc.generated.dsl

import dev.yidafu.swc.generated.*

/**
 * DSL (Domain-Specific Language) functions for building AST nodes and configuration objects.
 *
 * These functions provide a type-safe, builder-pattern API for creating SWC AST nodes
 * and configuration objects in a more idiomatic Kotlin way.
 *
 * @sample dev.yidafu.swc.sample.createExampleDsl
 *
 * ## Usage Examples
 *
 * ### Creating a Module
 * ```kotlin
 * val module = module {
 *     body = arrayOf(
 *         importDeclaration {
 *             specifiers = arrayOf(importDefaultSpecifier {
 *                 local = identifier {
 *                     value = "React"
 *                     span = emptySpan()
 *                 }
 *             })
 *             source = stringLiteral {
 *                 value = "react"
 *                 raw = "\"react\""
 *                 span = emptySpan()
 *             }
 *             span = emptySpan()
 *         }
 *     )
 * }
 * ```
 *
 * ### Creating Parser Options
 * ```kotlin
 * val tsOptions = tsParseOptions {
 *     tsx = true
 *     decorators = true
 * }
 *
 * val esOptions = esParseOptions {
 *     jsx = true
 *     allowReturnOutsideFunction = true
 * }
 * ```
 *
 * ### Creating Transform Options
 * ```kotlin
 * val transformOptions = options {
 *     jsc {
 *         target = JscTarget.ES2020
 *         parser {
 *             syntax = Syntax.TYPESCRIPT
 *             tsx = true
 *         }
 *     }
 *     module {
 *         type = ModuleConfigType.ES6
 *     }
 * }
 * ```
 */

/**
 * Create a Module AST node using DSL builder pattern.
 *
 * @param block Lambda to configure the module
 * @return Configured Module node
 *
 * @example
 * ```kotlin
 * val module = module {
 *     body = arrayOf(
 *         variableDeclaration {
 *             kind = VariableDeclarationKind.CONST
 *             declarations = arrayOf(variableDeclarator {
 *                 id = identifier { value = "x"; span = emptySpan() }
 *                 init = numericLiteral { value = 42.0; span = emptySpan() }
 *                 span = emptySpan()
 *             })
 *             span = emptySpan()
 *         }
 *     )
 * }
 * ```
 */
fun module(block: Module.() -> Unit): Module = createModule(block)

/**
 * Create transform Options using DSL builder pattern.
 *
 * @param block Lambda to configure the options
 * @return Configured Options object
 *
 * @example
 * ```kotlin
 * val opts = options {
 *     jsc {
 *         target = JscTarget.ES5
 *         parser {
 *             syntax = Syntax.ECMASCRIPT
 *         }
 *     }
 * }
 * ```
 */
fun options(block: Options.() -> Unit): Options = Options().apply(block)

/**
 * Create TypeScript parser configuration using DSL builder pattern.
 *
 * @param block Lambda to configure the parser
 * @return Configured TsParserConfig
 */
fun tsParserConfig(block: TsParserConfig.() -> Unit): TsParserConfig = createTsParserConfig(block)

/**
 * Create ECMAScript parser configuration using DSL builder pattern.
 *
 * @param block Lambda to configure the parser
 * @return Configured EsParserConfig
 */
fun esParserConfig(block: EsParserConfig.() -> Unit): EsParserConfig = createEsParserConfig(block)

/**
 * Create TypeScript parse options using DSL builder pattern.
 * This is a convenience function that wraps tsParserConfig.
 *
 * @param block Lambda to configure the parse options
 * @return Configured TsParserConfig
 *
 * @example
 * ```kotlin
 * val options = tsParseOptions {
 *     tsx = true
 *     decorators = true
 * }
 * ```
 */
fun tsParseOptions(block: TsParserConfig.() -> Unit = {}): TsParserConfig = tsParserConfig(block)

/**
 * Create ECMAScript parse options using DSL builder pattern.
 * This is a convenience function that wraps esParserConfig.
 *
 * @param block Lambda to configure the parse options
 * @return Configured EsParserConfig
 *
 * @example
 * ```kotlin
 * val options = esParseOptions {
 *     jsx = true
 *     allowReturnOutsideFunction = true
 * }
 * ```
 */
fun esParseOptions(block: EsParserConfig.() -> Unit = {}): EsParserConfig = esParserConfig(block)
