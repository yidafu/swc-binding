package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import kotlinx.serialization.ExperimentalSerializationApi

/**
 * @sample dev.yidafu.swc.sample.createExampleDsl
 */
//@OptIn(ExperimentalSerializationApi::class)
//fun module(block: Module.() -> Unit): Module = Module().apply(block)

fun options(block: Options.() -> Unit): Options = Options().apply(block)

//fun tsParserConfig(block: TsParserConfig.() -> Unit): TsParserConfig =
//    TsParserConfig().apply(block)
//
//fun esParserConfig(block: EsParserConfig.() -> Unit): EsParserConfig =
//    EsParserConfig().apply(block)
//
//fun tsParseOptions(block: TsParserConfig.() -> Unit = {}): TsParserConfig =
//    TsParserConfig().apply(block)
//
//fun esParseOptions(block: EsParserConfig.() -> Unit = {}): EsParserConfig =
//    EsParserConfig().apply(block)

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