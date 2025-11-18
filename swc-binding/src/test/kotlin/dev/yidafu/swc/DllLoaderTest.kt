package dev.yidafu.swc

import io.kotest.core.spec.style.AnnotationSpec
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DllLoaderTest : AnnotationSpec() {

    @Test
    fun `detect current platform`() {
        val platform = DllLoader.Platform.current
        assertNotNull(platform)
    }

    @Test
    fun `platform toString returns readable name`() {
        val platformStr = DllLoader.Platform.current.toString()
        assertNotNull(platformStr)
        assertTrue(platformStr.isNotEmpty())
    }

    @Test
    fun `Mac platform detection`() {
        val platform = DllLoader.Platform.current
        if (platform is DllLoader.Platform.Mac) {
            // 在 Mac 平台上测试
            val isIntel = platform.isIntel()
            val isM1 = platform.isM1()

            // 应该是 Intel 或 M1，不能两者都是
            assertTrue(isIntel || isM1)
            assertEquals("Mac", platform.toString())
        }
    }

    @Test
    fun `Linux platform methods`() {
        val platform = DllLoader.Platform.current
        if (platform is DllLoader.Platform.Linux) {
            val isArm = platform.isArm()
            val isIntel = platform.isIntel()

            // 应该是 ARM 或 Intel
            assertTrue(isArm || isIntel)
            assertEquals("Linux", platform.toString())
        }
    }

    @Test
    fun `Windows platform detection`() {
        val platform = DllLoader.Platform.current
        if (platform is DllLoader.Platform.Windows) {
            val isArm = platform.isArm()
            val isIntel = platform.isIntel()

            // 应该是 ARM 或 Intel
            assertTrue(isArm || isIntel)
            assertEquals("Windows", platform.toString())
        }
    }

    @Test
    fun `copyDll2Temp returns valid path`() {
        val path = DllLoader.copyDll2Temp("swc_jni")

        assertNotNull(path)
        assertTrue(path.isNotEmpty())
        assertTrue(path.contains("swc_jni") || path.contains("swc-jni"))
    }

    @Test
    fun `copyDll2Temp creates file in temp directory`() {
        val path = DllLoader.copyDll2Temp("swc_jni")

        assertTrue(path.startsWith(System.getProperty("java.io.tmpdir")))
    }

    @Test
    fun `copyDll2Temp is idempotent`() {
        val path1 = DllLoader.copyDll2Temp("swc_jni")
        val path2 = DllLoader.copyDll2Temp("swc_jni")

        // 多次调用应该返回相同的路径
        assertEquals(path1, path2)
    }

    @Test
    fun `outAbsPath is populated after copyDll2Temp`() {
        DllLoader.copyDll2Temp("swc_jni")

        assertNotNull(DllLoader.outAbsPath)
        assertTrue(DllLoader.outAbsPath.isNotEmpty())
    }

    @Test
    fun `platform detection covers all major platforms`() {
        val platform = DllLoader.Platform.current

        assertTrue(
            platform is DllLoader.Platform.Mac ||
                platform is DllLoader.Platform.Linux ||
                platform is DllLoader.Platform.Windows ||
                platform is DllLoader.Platform.Solaris ||
                platform is DllLoader.Platform.FreeBSD
        )
    }

    @Test
    fun `DLL path contains correct extension for platform`() {
        val path = DllLoader.copyDll2Temp("swc_jni")
        val platform = DllLoader.Platform.current

        when (platform) {
            is DllLoader.Platform.Mac -> assertTrue(path.endsWith(".dylib"))
            is DllLoader.Platform.Linux -> assertTrue(path.endsWith(".so"))
            is DllLoader.Platform.Windows -> assertTrue(path.endsWith(".dll"))
            else -> {} // Solaris, FreeBSD 不测试
        }
    }

    @Test
    fun `Mac M1 or Intel detection is consistent`() {
        val platform = DllLoader.Platform.current
        if (platform is DllLoader.Platform.Mac) {
            val isIntel = platform.isIntel()
            val isM1 = platform.isM1()

            // 不能既是 Intel 又是 M1
            assertTrue(!(isIntel && isM1))
        }
    }

    @Test
    fun `Linux ARM or Intel detection is consistent`() {
        val platform = DllLoader.Platform.current
        if (platform is DllLoader.Platform.Linux) {
            // 只是验证方法能调用，不会抛异常
            platform.isArm()
            platform.isIntel()
        }
    }

    @Test
    fun `Windows ARM or Intel detection is consistent`() {
        val platform = DllLoader.Platform.current
        if (platform is DllLoader.Platform.Windows) {
            val isArm = platform.isArm()
            val isIntel = platform.isIntel()

            // 不能既是 ARM 又是 Intel
            assertTrue(!(isArm && isIntel))
        }
    }

    @Test
    fun `DLL path contains platform-specific directory`() {
        val path = DllLoader.copyDll2Temp("swc_jni")
        val platform = DllLoader.Platform.current

        when (platform) {
            is DllLoader.Platform.Mac -> {
                assertTrue(path.contains("darwin-arm64-apple") || path.contains("darwin-x64-apple"))
            }
            is DllLoader.Platform.Linux -> {
                assertTrue(
                    path.contains("linux-x64-musl") || path.contains("linux-arm64-gnu")
                )
            }
            is DllLoader.Platform.Windows -> {
                assertTrue(path.contains("windows-x64-gnu") || path.contains("windows-arm64-gnu"))
            }
            else -> {}
        }
    }

    @Test
    fun `Solaris platform toString`() {
        val solaris = DllLoader.Platform.Solaris
        assertEquals("Solaris", solaris.toString())
    }

    @Test
    fun `FreeBSD platform toString`() {
        val freeBSD = DllLoader.Platform.FreeBSD
        assertEquals("FreeBSD", freeBSD.toString())
    }

    @Test
    fun `copyDll2Temp path includes library name`() {
        val path = DllLoader.copyDll2Temp("swc_jni")
        val platform = DllLoader.Platform.current

        when (platform) {
            is DllLoader.Platform.Mac -> assertTrue(path.contains("libswc_jni"))
            is DllLoader.Platform.Linux -> assertTrue(path.contains("libswc_jni"))
            is DllLoader.Platform.Windows -> assertTrue(path.contains("swc_jni"))
            else -> {}
        }
    }
}
