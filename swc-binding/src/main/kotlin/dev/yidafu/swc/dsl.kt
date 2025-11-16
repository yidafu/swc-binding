package dev.yidafu.swc

import dev.yidafu.swc.generated.*

/**
 * @sample dev.yidafu.swc.sample.createExampleDsl
 */
fun module(block: Module.() -> Unit): Module = ModuleImpl().apply(block)

fun options(block: Options.() -> Unit): Options = OptionsImpl().apply(block)

fun tsParserConfig(block: TsParserConfig.() -> Unit): TsParserConfig =
    TsParserConfigImpl().apply(block)

fun esParserConfig(block: EsParserConfig.() -> Unit): EsParserConfig =
    EsParserConfigImpl().apply(block)

fun tsParseOptions(block: TsParserConfig.() -> Unit = {}): TsParserConfig =
    TsParserConfigImpl().apply(block)

fun esParseOptions(block: EsParserConfig.() -> Unit = {}): EsParserConfig =
    EsParserConfigImpl().apply(block)

fun span(
    start: Int = 0,
    end: Int = 0,
    ctxt: Int = 0,
    block: Span.() -> Unit = {}
): Span = Span().apply {
     this.start = start
     this.end = end
      this.ctxt = ctxt
    block()
}

fun emptySpan(): Span = span(start = 0, end = 0, ctxt = 0)