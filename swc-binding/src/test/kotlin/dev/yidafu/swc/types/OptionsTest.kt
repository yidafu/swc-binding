package dev.yidafu.swc.generated

import dev.yidafu.swc.Union
import dev.yidafu.swc.generated.dsl.jscConfig
import dev.yidafu.swc.generated.dsl.matchPattern
import dev.yidafu.swc.options
import kotlin.test.assertEquals
import io.kotest.core.spec.style.AnnotationSpec
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test

class OptionsTest : AnnotationSpec() {
    @Test
    fun `encode options`() {
        val opt = options {
            cwd = "/path/to/cwd"
            configFile = Union.U2<String, Boolean>(b = false)
            swcrc = true
            isModule = Union.U3<Boolean, String, String>(a = true)
            jsc = jscConfig {
                loose = true
            }

            swcrcRoots = Union.U3<Boolean, MatchPattern, Array<MatchPattern>>(
                c = arrayOf(
                    matchPattern { }
                )
            )
        }

        val output = Json.encodeToString(opt)

        assertEquals(
            output,
            """{"cwd":"/path/to/cwd","configFile":false,"swcrc":true,"swcrcRoots":[{}],"isModule":true,"jsc":{"loose":true}}"""
        )
    }
}