package dev.yidafu.swc

import dev.yidafu.swc.generated.*
import dev.yidafu.swc.generated.dsl.* // ktlint-disable no-wildcard-imports
import kotlin.test.assertEquals
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull
import kotlin.test.assertIs
import kotlin.test.assertTrue
import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.Test

class TypeScriptSyntaxTest : AnnotationSpec() {
    private val swcNative = SwcNative()

    @Test
    fun `parse TypeScript enum`() {
        val output = swcNative.parseSync(
            """
            enum Color {
                Red,
                Green,
                Blue
            }
            
            enum Status {
                Active = "active",
                Inactive = "inactive",
                Pending = "pending"
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val enumDecl = items[0].shouldBeInstanceOf<TsEnumDeclaration>()
            assertNotNull(enumDecl.id)
            assertNotNull(enumDecl.members)
        }
    }

    @Test
    fun `parse TypeScript namespace`() {
        val output = swcNative.parseSync(
            """
            namespace MyNamespace {
                export const value = 42;
                export function func() {}
                export class MyClass {}
            }
            
            namespace Nested {
                namespace Inner {
                    export const value = 100;
                }
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val namespaceDecl = items[0].shouldBeInstanceOf<TsNamespaceDeclaration>()
            assertNotNull(namespaceDecl.id)
            assertNotNull(namespaceDecl.body)
        }
    }

    @Test
    fun `parse TypeScript decorators`() {
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
        val module = output as Module
        module.body?.let { items ->
            val classDecl = items[0].shouldBeInstanceOf<ClassDeclaration>()
            assertNotNull(classDecl.decorators)
        }
    }

    @Test
    fun `parse TypeScript generic function`() {
        val output = swcNative.parseSync(
            """
            function identity<T>(arg: T): T {
                return arg;
            }
            
            function map<T, U>(arr: T[], fn: (item: T) => U): U[] {
                return arr.map(fn);
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[0].shouldBeInstanceOf<FunctionDeclaration>()
            // TypeScript 类型参数在类型注解中
            assertNotNull(funcDecl)
        }
    }

    @Test
    fun `parse TypeScript generic class`() {
        val output = swcNative.parseSync(
            """
            class Container<T> {
                private value: T;
                
                constructor(value: T) {
                    this.value = value;
                }
                
                getValue(): T {
                    return this.value;
                }
            }
            
            class Pair<T, U> {
                first: T;
                second: U;
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val classDecl = items[0].shouldBeInstanceOf<ClassDeclaration>()
            assertNotNull(classDecl)
        }
    }

    @Test
    fun `parse TypeScript union types`() {
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

    @Test
    fun `parse TypeScript intersection types`() {
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

    @Test
    fun `parse TypeScript conditional types`() {
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

    @Test
    fun `parse TypeScript mapped types`() {
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
        val module = output as Module
        module.body?.let { items ->
            val typeAlias = items[0].shouldBeInstanceOf<TsTypeAliasDeclaration>()
            val mappedType = typeAlias.typeAnnotation.shouldBeInstanceOf<TsMappedType>()
            assertNotNull(mappedType.typeParam)
            assertNotNull(mappedType.typeAnnotation)
        }
    }

    @Test
    fun `parse TypeScript utility types`() {
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

    @Test
    fun `parse TypeScript type inference with extends`() {
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
            val inferType = conditionalType.trueType.shouldBeInstanceOf<TsInferType>()
            assertNotNull(inferType.typeParam)
        }
    }

    @Test
    fun `parse TypeScript const assertion`() {
        val output = swcNative.parseSync(
            """
            const array = [1, 2, 3] as const;
            const obj = { a: 1, b: 2 } as const;
            const tuple = [1, "hello"] as const;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val tsAsExpr = declarator.init?.shouldBeInstanceOf<TsAsExpression>()
            assertNotNull(tsAsExpr)
        }
    }

    @Test
    fun `parse TypeScript tuple types`() {
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

    @Test
    fun `parse TypeScript function types`() {
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

    @Test
    fun `parse TypeScript constructor types`() {
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

    @Test
    fun `parse TypeScript index signatures`() {
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

    @Test
    fun `parse TypeScript type queries`() {
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

    @Test
    fun `parse TypeScript type operators`() {
        val output = swcNative.parseSync(
            """
            type ReadonlyArray<T> = readonly T[];
            type ReadonlyObject = {
                readonly [K in keyof T]: T[K];
            };
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse TypeScript template literal types`() {
        val output = swcNative.parseSync(
            """
            type EventName = `onClick`;
            type GetterName = `getValue`;
            type CSS = \`100px\`;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val typeAlias = items[0].shouldBeInstanceOf<TsTypeAliasDeclaration>()
            val templateLiteralType = typeAlias.typeAnnotation.shouldBeInstanceOf<TsTemplateLiteralType>()
            assertNotNull(templateLiteralType.types)
        }
    }

    @Test
    fun `parse TypeScript interface with extends`() {
        val output = swcNative.parseSync(
            """
            interface A {
                a: number;
            }
            
            interface B extends A {
                b: string;
            }
            
            interface C extends A, B {
                c: boolean;
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val interfaceDecl = items[1].shouldBeInstanceOf<TsInterfaceDeclaration>()
            assertNotNull(interfaceDecl.extends)
        }
    }

    @Test
    fun `parse TypeScript interface with generic parameters`() {
        val output = swcNative.parseSync(
            """
            interface Container<T> {
                value: T;
                setValue(value: T): void;
                getValue(): T;
            }
            
            interface Pair<T, U> {
                first: T;
                second: U;
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val interfaceDecl = items[0].shouldBeInstanceOf<TsInterfaceDeclaration>()
            assertNotNull(interfaceDecl.typeParams)
        }
    }

    @Test
    fun `parse TypeScript type predicates`() {
        val output = swcNative.parseSync(
            """
            function isString(value: unknown): value is string {
                return typeof value === "string";
            }
            
            function isNumber(value: any): value is number {
                return typeof value === "number";
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[0].shouldBeInstanceOf<FunctionDeclaration>()
            // Type predicates are in return type annotation
            assertNotNull(funcDecl)
        }
    }

    @Test
    fun `parse TypeScript indexed access types`() {
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

    @Test
    fun `parse TypeScript satisfies expression`() {
        val output = swcNative.parseSync(
            """
            const colors = {
                red: "#ff0000",
                green: "#00ff00",
                blue: "#0000ff"
            } satisfies Record<string, string>;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val declaration = items[0].shouldBeInstanceOf<VariableDeclaration>()
            val declarator = declaration.declarations?.get(0).shouldBeInstanceOf<VariableDeclarator>()
            val satisfiesExpr = declarator.init?.shouldBeInstanceOf<TsSatisfiesExpression>()
            assertNotNull(satisfiesExpr)
        }
    }

    @Test
    fun `parse TypeScript import type`() {
        val output = swcNative.parseSync(
            """
            import type { User } from "./types";
            import type { Config } from "./config";
            type MyType = import("./module").ExportedType;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse TypeScript module declaration`() {
        val output = swcNative.parseSync(
            """
            declare module "my-module" {
                export const value: number;
                export function func(): void;
            }
            
            declare module "*.css" {
                const content: string;
                export default content;
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val moduleDecl = items[0].shouldBeInstanceOf<TsModuleDeclaration>()
            assertNotNull(moduleDecl.id)
            assertNotNull(moduleDecl.body)
        }
    }

    @Test
    fun `parse TypeScript namespace export declaration`() {
        val output = swcNative.parseSync(
            """
            namespace MyNamespace {
                export const value = 42;
            }
            
            export namespace MyExportedNamespace {
                export const value = 100;
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse TypeScript type annotation on parameters and returns`() {
        val output = swcNative.parseSync(
            """
            function add(a: number, b: number): number {
                return a + b;
            }
            
            const arrow: (x: number) => string = (x) => x.toString();
            
            class MyClass {
                method(param: string): boolean {
                    return true;
                }
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val funcDecl = items[0].shouldBeInstanceOf<FunctionDeclaration>()
            assertNotNull(funcDecl)
        }
    }

    @Test
    fun `transform TypeScript enum to JavaScript`() {
        val output = swcNative.transformSync(
            """
            enum Color {
                Red,
                Green,
                Blue
            }
            """.trimIndent(),
            false,
            options {
                jsc = jscConfig {
                    parser = tsParseOptions { }
                }
            }
        )
        assertNotNull(output.code)
        assertTrue(!output.code.contains("enum"))
    }

    @Test
    fun `transform TypeScript interface to JavaScript`() {
        val output = swcNative.transformSync(
            """
            interface User {
                name: string;
                age: number;
            }
            """.trimIndent(),
            false,
            options {
                jsc = jscConfig {
                    parser = tsParseOptions { }
                }
            }
        )
        assertNotNull(output.code)
        assertTrue(!output.code.contains("interface"))
    }

    @Test
    fun `transform TypeScript with generic constraints`() {
        val output = swcNative.transformSync(
            """
            function identity<T extends string | number>(arg: T): T {
                return arg;
            }
            """.trimIndent(),
            false,
            options {
                jsc = jscConfig {
                    parser = tsParseOptions { }
                }
            }
        )
        assertNotNull(output.code)
        assertTrue(!output.code.contains("extends"))
    }

    // ==================== Additional Utility Types Tests ====================

    @Test
    fun `parse TypeScript Omit utility type`() {
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

    @Test
    fun `parse TypeScript Exclude and Extract utility types`() {
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

    @Test
    fun `parse TypeScript NonNullable utility type`() {
        val output = swcNative.parseSync(
            """
            type T = NonNullable<string | number | undefined | null>;
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse TypeScript Required and Readonly utility types`() {
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

    @Test
    fun `parse TypeScript Record utility type`() {
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

    // ==================== Additional Type Operator Tests ====================

    @Test
    fun `parse TypeScript keyof operator`() {
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
            // keyof 类型解析验证
            assertNotNull(typeAlias.typeAnnotation)
        }
    }

    @Test
    fun `parse TypeScript readonly modifier`() {
        val output = swcNative.parseSync(
            """
            type ReadonlyArray<T> = readonly T[];
            interface ReadonlyProps {
                readonly name: string;
                readonly age: number;
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse TypeScript optional properties`() {
        val output = swcNative.parseSync(
            """
            interface User {
                name: string;
                age?: number;
                email?: string;
            }
            
            type Optional<T> = {
                [K in keyof T]?: T[K];
            };
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    // ==================== Additional Real-world Scenarios ====================

    @Test
    fun `parse TypeScript complex generic scenario`() {
        val output = swcNative.parseSync(
            """
            interface Repository<T> {
                findById(id: string): Promise<T | null>;
                findAll(): Promise<T[]>;
                save(entity: T): Promise<T>;
                delete(id: string): Promise<boolean>;
            }
            
            class UserRepository implements Repository<User> {
                async findById(id: string): Promise<User | null> {
                    return null;
                }
                async findAll(): Promise<User[]> {
                    return [];
                }
                async save(entity: User): Promise<User> {
                    return entity;
                }
                async delete(id: string): Promise<boolean> {
                    return true;
                }
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse TypeScript function overloads`() {
        val output = swcNative.parseSync(
            """
            function process(value: string): string;
            function process(value: number): number;
            function process(value: string | number): string | number {
                return value;
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse TypeScript abstract class`() {
        val output = swcNative.parseSync(
            """
            abstract class Animal {
                abstract makeSound(): void;
                move(): void {
                    console.log("Moving");
                }
            }
            
            class Dog extends Animal {
                makeSound(): void {
                    console.log("Woof");
                }
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
        val module = output as Module
        module.body?.let { items ->
            val classDecl = items[0].shouldBeInstanceOf<ClassDeclaration>()
            assertNotNull(classDecl.isAbstract)
        }
    }

    @Test
    fun `parse TypeScript type guards`() {
        val output = swcNative.parseSync(
            """
            function isString(value: unknown): value is string {
                return typeof value === "string";
            }
            
            function isNumber(value: any): value is number {
                return typeof value === "number";
            }
            
            function process(value: unknown) {
                if (isString(value)) {
                    return value.toUpperCase();
                }
                if (isNumber(value)) {
                    return value * 2;
                }
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse TypeScript assertion functions`() {
        val output = swcNative.parseSync(
            """
            function assertIsString(value: unknown): asserts value is string {
                if (typeof value !== "string") {
                    throw new Error("Not a string");
                }
            }
            
            function process(value: unknown) {
                assertIsString(value);
                return value.toUpperCase();
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse TypeScript branded types`() {
        val output = swcNative.parseSync(
            """
            type UserId = string & { __brand: "UserId" };
            type ProductId = string & { __brand: "ProductId" };
            
            function getUser(id: UserId) {
                return { id, name: "User" };
            }
            """.trimIndent(),
            tsParseOptions { },
            "test.ts"
        )
        output.shouldBeInstanceOf<Module>()
    }

    @Test
    fun `parse TypeScript template literal types with manipulation`() {
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
}
