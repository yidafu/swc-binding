package dev.yidafu.swc.swcnative.syntax.typescript

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull

/**
 * TypeScript type system tests
 * Split from SwcNativeTypeScriptTest
 */
class SwcNativeTypeScriptTypeSystemTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse TypeScript union types") {
        val output = swcNative.parseSync(
            """
            type StringOrNumber = string | number;
            type Status = "active" | "inactive" | "pending";
            type Mixed = string | number | boolean | null | undefined;
            
            function process(value: string | number) {
                return value;
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val typeAlias = items[0].shouldBeInstanceOf<TsTypeAliasDeclaration>()
            val unionType = typeAlias.typeAnnotation.shouldBeInstanceOf<TsUnionType>()
            assertNotNull(unionType.types)
        }
    }

    should("parse TypeScript intersection types") {
        val output = swcNative.parseSync(
            """
            type A = { a: number };
            type B = { b: string };
            type C = A & B;
            
            interface X { x: number; }
            interface Y { y: string; }
            type Z = X & Y;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val typeAlias = items[2].shouldBeInstanceOf<TsTypeAliasDeclaration>()
            val intersectionType = typeAlias.typeAnnotation.shouldBeInstanceOf<TsIntersectionType>()
            assertNotNull(intersectionType.types)
        }
    }

    should("parse TypeScript tuple types") {
        val output = swcNative.parseSync(
            """
            type Tuple = [string, number];
            type OptionalTuple = [string, number?];
            type RestTuple = [string, ...number[]];
            type NamedTuple = [name: string, age: number];
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val typeAlias = items[0].shouldBeInstanceOf<TsTypeAliasDeclaration>()
            val tupleType = typeAlias.typeAnnotation.shouldBeInstanceOf<TsTupleType>()
            assertNotNull(tupleType.elemTypes)
        }
    }

    should("parse TypeScript function types") {
        val output = swcNative.parseSync(
            """
            type Func = (x: number, y: number) => number;
            type AsyncFunc = (input: string) => Promise<string>;
            type OptionalParam = (x: number, y?: number) => number;
            type RestParam = (...args: number[]) => number;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val typeAlias = items[0].shouldBeInstanceOf<TsTypeAliasDeclaration>()
            val funcType = typeAlias.typeAnnotation.shouldBeInstanceOf<TsFunctionType>()
            assertNotNull(funcType.params)
            assertNotNull(funcType.typeAnnotation)
        }
    }

    should("parse TypeScript constructor types") {
        val output = swcNative.parseSync(
            """
            type Constructor = new (x: number) => MyClass;
            type GenericConstructor<T> = new (...args: any[]) => T;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val typeAlias = items[0].shouldBeInstanceOf<TsTypeAliasDeclaration>()
            val constructorType = typeAlias.typeAnnotation.shouldBeInstanceOf<TsConstructorType>()
            assertNotNull(constructorType.params)
            assertNotNull(constructorType.typeAnnotation)
        }
    }

    should("parse TypeScript index signatures") {
        val output = swcNative.parseSync(
            """
            interface Dictionary {
                [key: string]: number;
            }
            
            interface ArrayLike {
                [index: number]: string;
            }
            
            interface Mixed {
                name: string;
                [key: string]: string | number;
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val interfaceDecl = items[0].shouldBeInstanceOf<TsInterfaceDeclaration>()
            val body = interfaceDecl.body.shouldBeInstanceOf<TsInterfaceBody>()
            assertNotNull(body.body)
            val indexSig = body.body?.get(0).shouldBeInstanceOf<TsIndexSignature>()
            assertNotNull(indexSig.params)
            assertNotNull(indexSig.typeAnnotation)
        }
    }
})
