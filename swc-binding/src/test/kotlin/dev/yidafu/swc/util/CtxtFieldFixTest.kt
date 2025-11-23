package dev.yidafu.swc.util

import dev.yidafu.swc.SwcJson
import dev.yidafu.swc.generated.*
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.test.assertNotNull

/**
 * Tests for automatic fixing of missing `ctxt` fields in span objects.
 * 
 * This addresses the issue where Rust's serde skips serializing default values (ctxt = 0),
 * which causes deserialization failures in polymorphic scenarios where `coerceInputValues` doesn't work.
 */
class CtxtFieldFixTest : ShouldSpec({

    should("parseAstTree should automatically add missing ctxt field in simple span") {
        // JSON missing ctxt field (simulating Rust behavior)
        val jsonWithoutCtxt = """{"type":"Module","span":{"start":0,"end":0},"body":[],"interpreter":null}"""
        
        val program = SwcJson.parseAstTree(jsonWithoutCtxt)
        
        assertNotNull(program)
        program.shouldBeInstanceOf<Module>()
        val module = program as Module
        assertNotNull(module.span)
        module.span.ctxt shouldBe 0
    }

    should("parseAstTree should handle span with ctxt already present") {
        // JSON with ctxt field already present
        val jsonWithCtxt = """{"type":"Module","span":{"start":0,"end":0,"ctxt":5},"body":[],"interpreter":null}"""
        
        val program = SwcJson.parseAstTree(jsonWithCtxt)
        
        assertNotNull(program)
        program.shouldBeInstanceOf<Module>()
        val module = program as Module
        assertNotNull(module.span)
        module.span.ctxt shouldBe 5 // Should preserve existing ctxt value
    }

    should("parseAstTree should fix missing ctxt in nested AST nodes") {
        // JSON with nested nodes missing ctxt
        val jsonWithNested = """
        {
            "type": "Module",
            "span": {"start": 0, "end": 20},
            "body": [
                {
                    "type": "VariableDeclaration",
                    "span": {"start": 0, "end": 10},
                    "kind": "const",
                    "declare": false,
                    "declarations": [
                        {
                            "type": "VariableDeclarator",
                            "span": {"start": 6, "end": 9},
                            "id": {
                                "type": "Identifier",
                                "span": {"start": 6, "end": 7},
                                "value": "x",
                                "optional": false
                            },
                            "init": {
                                "type": "NumericLiteral",
                                "span": {"start": 10, "end": 11},
                                "value": 1,
                                "raw": "1"
                            }
                        }
                    ]
                }
            ],
            "interpreter": null
        }
        """.trimIndent()
        
        val program = SwcJson.parseAstTree(jsonWithNested)
        
        assertNotNull(program)
        program.shouldBeInstanceOf<Module>()
        val module = program as Module
        module.span.ctxt shouldBe 0
        
        // Verify nested nodes also have ctxt
        assertNotNull(module.body)
        val body = module.body!!
        body.size shouldBe 1
        
        val varDecl = body[0] as VariableDeclaration
        varDecl.span.ctxt shouldBe 0
        
        val declarator = varDecl.declarations!![0]
        declarator.span.ctxt shouldBe 0
        
        val identifier = declarator.id as Identifier
        identifier.span.ctxt shouldBe 0
    }

    should("parseAstTree should fix missing ctxt in polymorphic scenarios (MemberExpression.property)") {
        // This is the critical case where coerceInputValues fails in polymorphic scenarios
        val jsonWithPolymorphic = """
        {
            "type": "Module",
            "span": {"start": 0, "end": 30},
            "body": [
                {
                    "type": "ExpressionStatement",
                    "span": {"start": 0, "end": 29},
                    "expression": {
                        "type": "MemberExpression",
                        "span": {"start": 0, "end": 28},
                        "obj": {
                            "type": "Identifier",
                            "span": {"start": 0, "end": 5},
                            "value": "console",
                            "optional": false
                        },
                        "property": {
                            "type": "Identifier",
                            "span": {"start": 6, "end": 9},
                            "value": "log",
                            "optional": false
                        },
                        "computed": false
                    }
                }
            ],
            "interpreter": null
        }
        """.trimIndent()
        
        // This should work even though property is polymorphic (Node? type)
        // and the Identifier is missing ctxt field
        val program = SwcJson.parseAstTree(jsonWithPolymorphic)
        
        assertNotNull(program)
        program.shouldBeInstanceOf<Module>()
        val module = program as Module
        assertNotNull(module.body)
        
        val exprStmt = module.body!![0] as ExpressionStatement
        val memberExpr = exprStmt.expression as MemberExpression
        val property = memberExpr.property as Identifier
        
        // Verify ctxt was added to the polymorphic property
        property.span.ctxt shouldBe 0
    }

    should("parseAstTree should handle multiple missing ctxt fields") {
        val jsonMultipleMissing = """
        {
            "type": "Module",
            "span": {"start": 0, "end": 50},
            "body": [
                {
                    "type": "VariableDeclaration",
                    "span": {"start": 0, "end": 10},
                    "kind": "const",
                    "declare": false,
                    "declarations": [
                        {
                            "type": "VariableDeclarator",
                            "span": {"start": 6, "end": 9},
                            "id": {
                                "type": "Identifier",
                                "span": {"start": 6, "end": 7},
                                "value": "a",
                                "optional": false
                            },
                            "init": null
                        }
                    ]
                },
                {
                    "type": "VariableDeclaration",
                    "span": {"start": 12, "end": 22},
                    "kind": "const",
                    "declare": false,
                    "declarations": [
                        {
                            "type": "VariableDeclarator",
                            "span": {"start": 18, "end": 21},
                            "id": {
                                "type": "Identifier",
                                "span": {"start": 18, "end": 19},
                                "value": "b",
                                "optional": false
                            },
                            "init": null
                        }
                    ]
                }
            ],
            "interpreter": null
        }
        """.trimIndent()
        
        val program = SwcJson.parseAstTree(jsonMultipleMissing)
        
        assertNotNull(program)
        program.shouldBeInstanceOf<Module>()
        val module = program as Module
        
        // Verify all spans have ctxt
        module.span.ctxt shouldBe 0
        
        module.body!!.forEach { item ->
            val varDecl = item as VariableDeclaration
            varDecl.span.ctxt shouldBe 0
            
            varDecl.declarations!!.forEach { decl ->
                decl.span.ctxt shouldBe 0
                val id = decl.id as Identifier
                id.span.ctxt shouldBe 0
            }
        }
    }

    should("SwcJson.parseAstTree should also fix missing ctxt fields") {
        val jsonWithoutCtxt = """{"type":"Module","span":{"start":0,"end":0},"body":[],"interpreter":null}"""
        
        val program = SwcJson.parseAstTree(jsonWithoutCtxt)
        
        assertNotNull(program)
        program.shouldBeInstanceOf<Module>()
        val module = program as Module
        module.span.ctxt shouldBe 0
    }

    should("parseAstTree should preserve existing ctxt values when fixing others") {
        val jsonMixed = """
        {
            "type": "Module",
            "span": {"start": 0, "end": 20},
            "body": [
                {
                    "type": "VariableDeclaration",
                    "span": {"start": 0, "end": 10, "ctxt": 3},
                    "kind": "const",
                    "declare": false,
                    "declarations": [
                        {
                            "type": "VariableDeclarator",
                            "span": {"start": 6, "end": 9},
                            "id": {
                                "type": "Identifier",
                                "span": {"start": 6, "end": 7},
                                "value": "x",
                                "optional": false
                            },
                            "init": null
                        }
                    ]
                }
            ],
            "interpreter": null
        }
        """.trimIndent()
        
        val program = SwcJson.parseAstTree(jsonMixed)
        
        assertNotNull(program)
        program.shouldBeInstanceOf<Module>()
        val module = program as Module
        
        // Module span should have ctxt = 0 (added)
        module.span.ctxt shouldBe 0
        
        // VariableDeclaration span should preserve ctxt = 3
        val varDecl = module.body!![0] as VariableDeclaration
        varDecl.span.ctxt shouldBe 3
        
        // VariableDeclarator span should have ctxt = 0 (added)
        varDecl.declarations!![0].span.ctxt shouldBe 0
        
        // Identifier span should have ctxt = 0 (added)
        val identifier = varDecl.declarations!![0].id as Identifier
        identifier.span.ctxt shouldBe 0
    }

    should("parseAstTree should handle span with whitespace") {
        val jsonWithWhitespace = """{"type":"Module","span": {"start": 0, "end": 0 },"body":[],"interpreter":null}"""
        
        val program = SwcJson.parseAstTree(jsonWithWhitespace)
        
        assertNotNull(program)
        program.shouldBeInstanceOf<Module>()
        val module = program as Module
        module.span.ctxt shouldBe 0
    }

    should("parseAstTree should handle complex nested structures with missing ctxt") {
        val jsonComplex = """
        {
            "type": "Module",
            "span": {"start": 0, "end": 100},
            "body": [
                {
                    "type": "FunctionDeclaration",
                    "span": {"start": 0, "end": 50},
                    "identifier": {
                        "type": "Identifier",
                        "span": {"start": 9, "end": 12},
                        "value": "add",
                        "optional": false
                    },
                    "declare": false,
                    "async": false,
                    "generator": false,
                    "params": [
                        {
                            "type": "Parameter",
                            "span": {"start": 13, "end": 14},
                            "pat": {
                                "type": "Identifier",
                                "span": {"start": 13, "end": 14},
                                "value": "a",
                                "optional": false
                            }
                        }
                    ],
                    "body": {
                        "type": "BlockStatement",
                        "span": {"start": 16, "end": 49},
                        "stmts": [
                            {
                                "type": "ReturnStatement",
                                "span": {"start": 20, "end": 47},
                                "argument": {
                                    "type": "Identifier",
                                    "span": {"start": 27, "end": 28},
                                    "value": "a",
                                    "optional": false
                                }
                            }
                        ]
                    }
                }
            ],
            "interpreter": null
        }
        """.trimIndent()
        
        val program = SwcJson.parseAstTree(jsonComplex)
        
        assertNotNull(program)
        program.shouldBeInstanceOf<Module>()
        
        // Verify all spans have ctxt field
        fun checkSpan(node: Node) {
            // Always check span.ctxt for any node with HasSpan
            if (node is HasSpan) {
                node.span.ctxt shouldBe 0
            }
            
            when (node) {
                is Module -> {
                    node.body?.forEach { item -> 
                        if (item is Node) {
                            checkSpan(item)
                        }
                    }
                }
                is FunctionDeclaration -> {
                    // Check identifier span directly
                    if (node.identifier is HasSpan) {
                        (node.identifier as HasSpan).span.ctxt shouldBe 0
                    }
                    // Check params
                    node.params?.forEach { param ->
                        if (param is Node) {
                            checkSpan(param)
                        }
                        // Check param.pat if it's a Node
                        param.pat?.let { pat ->
                            if (pat is Node && pat is HasSpan) {
                                (pat as HasSpan).span.ctxt shouldBe 0
                            }
                        }
                    }
                    // Check body
                    node.body?.let { body ->
                        if (body is Node) {
                            checkSpan(body)
                        }
                    }
                }
                is BlockStatement -> {
                    node.stmts?.forEach { stmt ->
                        if (stmt is Node) {
                            checkSpan(stmt)
                        }
                    }
                }
                is ReturnStatement -> {
                    // Check argument span directly
                    node.argument?.let { arg ->
                        if (arg is HasSpan) {
                            (arg as HasSpan).span.ctxt shouldBe 0
                        }
                    }
                }
                is Param -> {
                    // Param is already checked above via HasSpan
                    // Check pat if it's a Node
                    node.pat?.let { pat ->
                        if (pat is Node && pat is HasSpan) {
                            (pat as HasSpan).span.ctxt shouldBe 0
                        }
                    }
                }
                else -> {
                    // For other node types, span is already checked above
                }
            }
        }
        
        checkSpan(program)
    }
})

