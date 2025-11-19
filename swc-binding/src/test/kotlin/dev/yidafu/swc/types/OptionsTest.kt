package dev.yidafu.swc.generated

import dev.yidafu.swc.Union
import dev.yidafu.swc.generated.dsl.jscConfig
import dev.yidafu.swc.generated.dsl.options
import io.kotest.core.spec.style.AnnotationSpec
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class OptionsTest : AnnotationSpec() {
    private val testJson = Json {
        // Don't include null values - only serialize non-null fields
        explicitNulls = false
        // Include default values
        encodeDefaults = true
    }

    @Test
    fun `encode options`() {
        val opt = options {
            cwd = "/path/to/cwd"
            configFile = Union.U2<String, Boolean>(b = false)
            swcrc = true
            isModule = Union.U2<Boolean, String>(a = true)
            jsc = jscConfig {
                loose = true
            }

            swcrcRoots = Union.U3<Boolean, MatchPattern, Array<MatchPattern>>(
                c = arrayOf(
                    MatchPattern()
                )
            )
        }

        val output = testJson.encodeToString(opt)

        assertEquals(
            output,
            """{"cwd":"/path/to/cwd","configFile":false,"swcrc":true,"swcrcRoots":[{}],"isModule":true,"jsc":{"loose":true}}"""
        )
    }
}