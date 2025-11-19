package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
/**
 * TypeScript decorators tests
 * Split from SwcNativeTypeScriptTest
 */
class SwcNativeTypeScriptDecoratorTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse TypeScript decorators") {
        val output = swcNative.parseSync(
            """
            @decorator
            class MyClass {
                @propDecorator
                property: string;
                
                @methodDecorator
                method() {}
            }
            
            @decorator
            function func() {}
            """.trimIndent(),
            tsParseOptions {
                decorators = true
            },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }
})
