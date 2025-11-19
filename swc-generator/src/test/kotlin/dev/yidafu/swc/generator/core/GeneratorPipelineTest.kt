package dev.yidafu.swc.generator.core

import dev.yidafu.swc.generator.config.Configuration
import dev.yidafu.swc.generator.di.DependencyContainer
import dev.yidafu.swc.generator.result.ErrorCode
import dev.yidafu.swc.generator.result.GeneratorResult
import dev.yidafu.swc.generator.result.GeneratorResultFactory
import io.kotest.assertions.fail
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

class GeneratorPipelineTest : ShouldSpec({

    val config = Configuration.default()

    should("test create default pipeline") {
        val pipeline = GeneratorPipeline.createDefault(config)

        pipeline.shouldNotBeNull()
    }

    should("test add custom stage") {
        val pipeline = GeneratorPipeline(config, DependencyContainer(config))
        val customStage = object : AbstractStage<String, String>() {
            override val name: String = "CustomStage"
            override fun doExecute(input: String, context: PipelineContext): GeneratorResult<String> {
                return GeneratorResultFactory.success("processed")
            }
        }

        pipeline.addStage(customStage)

        val result = pipeline.execute("test")
        result.isSuccess().shouldBeTrue()
    }

    should("test pipeline execution with multiple stages") {
        val pipeline = GeneratorPipeline(config, DependencyContainer(config))

        val stage1 = object : AbstractStage<String, Int>() {
            override val name: String = "Stage1"
            override fun doExecute(input: String, context: PipelineContext): GeneratorResult<Int> {
                return GeneratorResultFactory.success(input.length)
            }
        }

        val stage2 = object : AbstractStage<Int, String>() {
            override val name: String = "Stage2"
            override fun doExecute(input: Int, context: PipelineContext): GeneratorResult<String> {
                return GeneratorResultFactory.success("result-$input")
            }
        }

        pipeline.addStage(stage1)
        pipeline.addStage(stage2)

        val result = pipeline.execute("test")
        result.isSuccess().shouldBeTrue()
    }

    should("test pipeline execution failure stops processing") {
        val pipeline = GeneratorPipeline(config, DependencyContainer(config))

        val failingStage = object : AbstractStage<String, String>() {
            override val name: String = "FailingStage"
            override fun doExecute(input: String, context: PipelineContext): GeneratorResult<String> {
                return GeneratorResultFactory.failure(
                    ErrorCode.UNKNOWN,
                    "Test failure"
                )
            }
        }

        val neverReachedStage = object : AbstractStage<String, String>() {
            override val name: String = "NeverReached"
            override fun doExecute(input: String, context: PipelineContext): GeneratorResult<String> {
                fail("This stage should not be executed")
                return GeneratorResultFactory.success("")
            }
        }

        pipeline.addStage(failingStage)
        pipeline.addStage(neverReachedStage)

        val result = pipeline.execute("test")
        result.isFailure().shouldBeTrue()
    }

    should("test pipeline context is passed between stages") {
        val pipeline = GeneratorPipeline(config, DependencyContainer(config))
        val contextKey = "testKey"
        val contextValue = "testValue"

        val stage1 = object : AbstractStage<String, String>() {
            override val name: String = "Stage1"
            override fun doExecute(input: String, context: PipelineContext): GeneratorResult<String> {
                context.setMetadata(contextKey, contextValue)
                return GeneratorResultFactory.success(input)
            }
        }

        val stage2 = object : AbstractStage<String, String>() {
            override val name: String = "Stage2"
            override fun doExecute(input: String, context: PipelineContext): GeneratorResult<String> {
                val value = context.getMetadata<String>(contextKey)
                value shouldBe contextValue
                return GeneratorResultFactory.success(input)
            }
        }

        pipeline.addStage(stage1)
        pipeline.addStage(stage2)

        val result = pipeline.execute("test")
        result.isSuccess().shouldBeTrue()
    }

    should("test pipeline handles exceptions") {
        val pipeline = GeneratorPipeline(config, DependencyContainer(config))

        val throwingStage = object : AbstractStage<String, String>() {
            override val name: String = "ThrowingStage"
            override fun doExecute(input: String, context: PipelineContext): GeneratorResult<String> {
                throw RuntimeException("Test exception")
            }
        }

        pipeline.addStage(throwingStage)

        val result = pipeline.execute("test")
        result.isFailure().shouldBeTrue()
    }
})
