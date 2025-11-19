package dev.yidafu.swc.generator.core

import dev.yidafu.swc.generator.config.Configuration
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

class ContextTest : ShouldSpec({

    val config = Configuration.default()

    should("test context creation") {
        val context = PipelineContext(config)
        context.shouldNotBeNull()
        context.configuration shouldBe config
    }

    should("test set and get metadata") {
        val context = PipelineContext(config)
        val key = "testKey"
        val value = "testValue"

        context.setMetadata(key, value)
        val retrieved = context.getMetadata<String>(key)

        retrieved shouldBe value
    }

    should("test get non-existent metadata returns null") {
        val context = PipelineContext(config)
        val retrieved = context.getMetadata<String>("nonExistent")

        retrieved.shouldBeNull()
    }

    should("test metadata with different types") {
        val context = PipelineContext(config)

        context.setMetadata("string", "value")
        context.setMetadata("int", 42)
        context.setMetadata("boolean", true)

        context.getMetadata<String>("string") shouldBe "value"
        context.getMetadata<Int>("int") shouldBe 42
        context.getMetadata<Boolean>("boolean") shouldBe true
    }

    should("test overwrite metadata") {
        val context = PipelineContext(config)
        val key = "key"

        context.setMetadata(key, "old")
        context.setMetadata(key, "new")

        context.getMetadata<String>(key) shouldBe "new"
    }

    should("test get metadata with wrong type returns null") {
        val context = PipelineContext(config)

        context.setMetadata("key", "string")
        val intValue = context.getMetadata<Int>("key")

        intValue.shouldBeNull()
    }
})
