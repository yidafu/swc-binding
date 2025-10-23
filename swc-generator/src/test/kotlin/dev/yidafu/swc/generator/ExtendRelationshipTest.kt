package dev.yidafu.swc.generator

import dev.yidafu.swc.generator.core.relation.ExtendRelationship
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ExtendRelationshipTest {

    @Test
    fun `test add and find relationship`() {
        ExtendRelationship.clear()

        ExtendRelationship.addRelation("Node", "Expression")
        ExtendRelationship.addRelation("Expression", "BinaryExpression")

        val parents = ExtendRelationship.findParentsByChild("Expression")
        assertTrue(parents.contains("Node"))

        val children = ExtendRelationship.findAllChildrenByParent("Node")
        assertTrue(children.contains("Expression"))
        assertTrue(children.contains("BinaryExpression"))
    }

    @Test
    fun `test find grand children`() {
        ExtendRelationship.clear()

        ExtendRelationship.addRelation("Node", "Expression")
        ExtendRelationship.addRelation("Expression", "BinaryExpression")
        ExtendRelationship.addRelation("Expression", "UnaryExpression")

        val grandChildren = ExtendRelationship.findAllGrandChildren("Node")
        assertTrue(grandChildren.contains("BinaryExpression"))
        assertTrue(grandChildren.contains("UnaryExpression"))
        assertEquals(2, grandChildren.size)
    }

    @Test
    fun `test get common parent`() {
        ExtendRelationship.clear()

        ExtendRelationship.addRelation("Node", "Expression")
        ExtendRelationship.addRelation("Expression", "BinaryExpression")
        ExtendRelationship.addRelation("Expression", "UnaryExpression")

        val commonParent = ExtendRelationship.getCommonParent(
            listOf("BinaryExpression", "UnaryExpression")
        )
        assertEquals("Expression", commonParent)
    }

    @Test
    fun `test get root`() {
        ExtendRelationship.clear()

        ExtendRelationship.addRelation("Node", "Expression")
        ExtendRelationship.addRelation("Expression", "BinaryExpression")

        val root = ExtendRelationship.getRoot("BinaryExpression")
        assertEquals("Node", root)
    }
}
