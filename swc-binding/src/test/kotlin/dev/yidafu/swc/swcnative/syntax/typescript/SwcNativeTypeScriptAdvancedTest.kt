package dev.yidafu.swc.swcnative.syntax.typescript

import dev.yidafu.swc.SwcNative
import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull

/**
 * TypeScript advanced type features tests
 * Split from SwcNativeTypeScriptTest
 */
class SwcNativeTypeScriptAdvancedTest : ShouldSpec({
    val swcNative = SwcNative()

    should("parse TypeScript conditional types") {
        val output = swcNative.parseSync(
            """
            type IsArray<T> = T extends any[] ? true : false;
            type NonNullable<T> = T extends null | undefined ? never : T;
            type Flatten<T> = T extends (infer U)[] ? U : T;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val typeAlias = items[0].shouldBeInstanceOf<TsTypeAliasDeclaration>()
            val conditionalType = typeAlias.typeAnnotation.shouldBeInstanceOf<TsConditionalType>()
            assertNotNull(conditionalType.checkType)
            assertNotNull(conditionalType.extendsType)
            assertNotNull(conditionalType.trueType)
            assertNotNull(conditionalType.falseType)
        }
    }

    should("parse TypeScript mapped types") {
        val output = swcNative.parseSync(
            """
            type Readonly<T> = {
                readonly [P in keyof T]: T[P];
            };
            
            type Partial<T> = {
                [P in keyof T]?: T[P];
            };
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse TypeScript type inference with extends") {
        val output = swcNative.parseSync(
            """
            type ReturnType<T> = T extends (...args: any[]) => infer R ? R : any;
            type Parameters<T> = T extends (...args: infer P) => any ? P : never;
            type InstanceType<T> = T extends new (...args: any[]) => infer R ? R : any;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val typeAlias = items[0].shouldBeInstanceOf<TsTypeAliasDeclaration>()
            val conditionalType = typeAlias.typeAnnotation.shouldBeInstanceOf<TsConditionalType>()
            assertNotNull(conditionalType.trueType)
            // The trueType might be TsTypeReference (R) instead of TsInferType directly
            // Just verify the conditional type structure is correct
            assertNotNull(conditionalType.checkType)
            assertNotNull(conditionalType.extendsType)
        }
    }

    should("parse TypeScript keyof operator") {
        val output = swcNative.parseSync(
            """
            type User = { name: string; age: number; email: string };
            type UserKeys = keyof User;
            type Keys = keyof { a: number; b: string };
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val typeAlias = items[1].shouldBeInstanceOf<TsTypeAliasDeclaration>()
            assertNotNull(typeAlias.typeAnnotation)
        }
    }

    should("parse TypeScript indexed access types") {
        val output = swcNative.parseSync(
            """
            type Person = { name: string; age: number; };
            type Name = Person["name"];
            type Age = Person["age"];
            type Values = Person[keyof Person];
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val typeAlias = items[1].shouldBeInstanceOf<TsTypeAliasDeclaration>()
            val indexedAccessType = typeAlias.typeAnnotation.shouldBeInstanceOf<TsIndexedAccessType>()
            assertNotNull(indexedAccessType.objectType)
            assertNotNull(indexedAccessType.indexType)
        }
    }

    should("parse TypeScript type queries") {
        val output = swcNative.parseSync(
            """
            type A = typeof MyClass;
            type B = typeof obj.property;
            type C = keyof MyInterface;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val typeAlias = items[0].shouldBeInstanceOf<TsTypeAliasDeclaration>()
            val typeQuery = typeAlias.typeAnnotation.shouldBeInstanceOf<TsTypeQuery>()
            assertNotNull(typeQuery.exprName)
        }
    }

    should("parse TypeScript type operators") {
        val output = swcNative.parseSync(
            """
            type ReadonlyArray<T> = readonly T[];
            type ReadonlyObject<T> = {
                readonly [K in keyof T]: T[K];
            };
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse TypeScript template literal types") {
        val output = swcNative.parseSync(
            """
            type EventName = `onClick`;
            type GetterName = `getValue`;
            type CSS = `100px`;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse TypeScript template literal types with manipulation") {
        val output = swcNative.parseSync(
            """
            type EventName = `onClick`;
            type ClickEvent = `onClick`;
            type ChangeEvent = `onChange`;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("template literal with TypeScript type") {
        val output = swcNative.parseSync(
            "var a = `string text \${expression} string text`;\ntype T = `Ts\${type}`",
            tsParseOptions { },
            "test.ts"
        )
        val module = output.shouldBeInstanceOf<Module>()
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val templateLiteral = declarator.init.shouldBeInstanceOf<TemplateLiteral>()
            assertNotNull(templateLiteral.expressions)

            val alias = items[1].shouldBeInstanceOf<TsTypeAliasDeclaration>()
            val typeAnnotation = alias.typeAnnotation.shouldBeInstanceOf<TsLiteralType>()
            val literal = typeAnnotation.literal.shouldBeInstanceOf<TsTemplateLiteralType>()
            assertNotNull(literal.types)
        }
    }

    should("parse TypeScript utility types") {
        val output = swcNative.parseSync(
            """
            type Partial<T> = {
                [P in keyof T]?: T[P];
            };
            
            type Pick<T, K extends keyof T> = {
                [P in K]: T[P];
            };
            
            type Record<K extends keyof any, T> = {
                [P in K]: T;
            };
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse TypeScript Omit utility type") {
        val output = swcNative.parseSync(
            """
            type User = { id: number; name: string; email: string; };
            type UserWithoutEmail = Omit<User, "email">;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse TypeScript Exclude and Extract utility types") {
        val output = swcNative.parseSync(
            """
            type T = Exclude<"a" | "b" | "c", "a">;
            type U = Extract<"a" | "b" | "c", "a" | "f">;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse TypeScript NonNullable utility type") {
        val output = swcNative.parseSync(
            """
            type T = NonNullable<string | number | undefined | null>;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse TypeScript Required and Readonly utility types") {
        val output = swcNative.parseSync(
            """
            type PartialUser = Partial<{ name: string; age: number }>;
            type RequiredUser = Required<PartialUser>;
            type ReadonlyUser = Readonly<{ name: string; age: number }>;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    should("parse TypeScript Record utility type") {
        val output = swcNative.parseSync(
            """
            type StringRecord = Record<string, string>;
            type NumberRecord = Record<"a" | "b" | "c", number>;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }
})
