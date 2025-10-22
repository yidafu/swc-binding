package dev.yidafu.swc

import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

object DllLoader {
    var outAbsPath: String = ""

    sealed class Platform {
        /**
         * Linux platform detection and resource path mapping.
         * Uses musl static linking for better compatibility.
         * Resource paths: linux-x64-musl/ or linux-arm64-musl/
         */
        object Linux : Platform() {
            private val cpuArch: String by lazy {
                System.getProperty("os.arch")
            }
            override fun toString(): String {
                return "Linux"
            }

            fun isArm(): Boolean {
                return cpuArch.contains("arm")
            }

            fun isIntel(): Boolean {
                return cpuArch.startsWith("x")
            }
        }

        /**
         * macOS platform detection and resource path mapping.
         * Uses Apple native toolchain for optimal performance.
         * Resource paths: darwin-x64-apple/ or darwin-arm64-apple/
         */
        object Mac : Platform() {
            private val cpuBrand: String by lazy {
                val pb = ProcessBuilder("sysctl", "-n", "machdep.cpu.brand_string")
                val p = pb.start()
                val br = p.inputReader()
                val details = br.readLine()
                p.waitFor()
                details
            }
            fun isIntel(): Boolean {
                return cpuBrand.contains("Intel")
            }
            fun isM1(): Boolean {
                return cpuBrand.contains("Apple")
            }

            override fun toString(): String {
                return "Mac"
            }
        }
        /**
         * Windows platform detection and resource path mapping.
         * Uses GNU toolchain for better cross-platform compatibility.
         * Resource paths: windows-x64-gnu/ or windows-arm64-gnu/
         */
        object Windows : Platform() {
            private val cpuArch: String by lazy {
                System.getProperty("os.arch")
            }
            
            fun isArm(): Boolean {
                return cpuArch.contains("aarch64") || cpuArch.contains("arm64")
            }
            
            fun isIntel(): Boolean {
                return cpuArch.startsWith("x") || cpuArch.contains("amd64")
            }
            
            override fun toString(): String {
                return "Windows"
            }
        }

        object Solaris : Platform() {
            override fun toString(): String {
                return "Solaris"
            }
        }

        object FreeBSD : Platform() {
            override fun toString(): String {
                return "FreeBSD"
            }
        }
        companion object {
            /**
             * Detect the current platform and return the appropriate Platform instance.
             * 
             * Supported platforms and their resource directories:
             * - Linux: linux-x64-musl/ or linux-arm64-gnu/
             * - macOS: darwin-x64-apple/ or darwin-arm64-apple/
             * - Windows: windows-x64-gnu/ or windows-arm64-gnu/
             */
            val current by lazy {
                val osName = System.getProperty("os.name")
                when {
                    osName.startsWith("Linux") -> Linux
                    osName.startsWith("Mac") || osName.startsWith("Darwin") -> Mac
                    osName.startsWith("Windows") -> Windows
                    osName.startsWith("Solaris") || osName.startsWith("SunOS") -> Solaris
                    osName.startsWith("FreeBSD") -> FreeBSD
                    else -> { throw UnsatisfiedLinkError("Unsupported OS: $osName") }
                }
            }
        }
    }

    /**
     * Copy the native library to a temporary directory and return the absolute path.
     * 
     * Resource directory structure:
     * - darwin-x64-apple/    - macOS Intel (Apple toolchain)
     * - darwin-arm64-apple/  - macOS ARM64 (Apple toolchain)
     * - linux-x64-musl/      - Linux x64 (musl static linking)
     * - linux-arm64-gnu/     - Linux ARM64 (GNU toolchain)
     * - windows-x64-gnu/     - Windows x64 (GNU toolchain)
     * - windows-arm64-gnu/   - Windows ARM64 (GNU toolchain)
     */
    fun copyDll2Temp(libName: String): String {
        val jarPath = when (val p = Platform.current) {
            is Platform.Linux -> (if (p.isArm()) "linux-arm64-gnu" else "linux-x64-musl") + "/lib$libName.so"
            is Platform.Mac -> (if (p.isIntel()) "darwin-x64-apple" else "darwin-arm64-apple") + "/lib$libName.dylib"
            is Platform.Windows -> (if (p.isArm()) "windows-arm64-gnu" else "windows-x64-gnu") + "/$libName.dll"
//            Platform.SOLARIS -> TODO()
//            Platform.FREEBSD -> TODO()
//            Platform.UNSPECIFIED -> TODO()
            else -> {
                throw UnsatisfiedLinkError("Unsupported platform $p")
            }
        }

        if (outAbsPath.isEmpty()) {
            synchronized(outAbsPath) {
                // TODO: add version postfix
                outAbsPath = System.getProperty("java.io.tmpdir") + "/swc-jni/" + jarPath
                val inStream = DllLoader::class.java.classLoader.getResourceAsStream(jarPath)!!
                val outPath = Paths.get(outAbsPath)
                Files.createDirectories(outPath.parent)
                Files.copy(inStream, outPath, StandardCopyOption.REPLACE_EXISTING)
            }
        }
        return outAbsPath
    }
}