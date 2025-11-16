package dev.yidafu.swc.generator.codegen.generator.types

/**
 * 负责 types.kt 的文本后处理：导入排序、空行、缩进修复等。
 */
class TypesPostProcessor {
    fun format(content: String): String {
        val lines = content.lines()
        val packageLine = lines.find { it.startsWith("package ") } ?: ""
        val importLines = lines.filter { it.startsWith("import ") }
        val otherLines = lines.filter { !it.startsWith("import ") && !it.startsWith("package ") }

        val reorderedImports = reorderImports(importLines)
        val processedOtherLines = processOtherLines(otherLines)

        val finalLines = mutableListOf<String>()
        if (packageLine.isNotEmpty()) {
            finalLines.add(packageLine)
            finalLines.add("")
        }
        if (reorderedImports.isNotEmpty()) {
            finalLines.addAll(reorderedImports)
            finalLines.add("")
        }
        finalLines.addAll(processedOtherLines)

        val cleanedLines = removeExtraEmptyLines(finalLines)
        return cleanedLines.joinToString("\n")
    }

    private fun reorderImports(importLines: List<String>): List<String> {
        val devYidafuImports = importLines.filter { it.startsWith("import dev.yidafu.swc") }
        val kotlinxImports = importLines.filter { it.startsWith("import kotlinx.serialization") }
        val kotlinImports = importLines.filter { it.startsWith("import kotlin") && !it.startsWith("import kotlinx") }
        val otherImports = importLines.filter {
            !it.startsWith("import dev.yidafu.swc") &&
                !it.startsWith("import kotlinx.serialization") &&
                !it.startsWith("import kotlin") &&
                !it.startsWith("import Record")
        }
        return devYidafuImports + kotlinxImports + kotlinImports + otherImports
    }

    private fun removeExtraEmptyLines(lines: List<String>): List<String> {
        val result = mutableListOf<String>()
        var i = 0
        while (i < lines.size) {
            val line = lines[i]
            if (line.trim().isEmpty()) {
                if (i + 1 < lines.size && lines[i + 1].trim().startsWith("@")) {
                    i++
                    continue
                }
                if (i + 1 < lines.size && lines[i + 1].trim().isEmpty()) {
                    i++
                    continue
                }
            }
            result.add(line)
            i++
        }
        return result
    }

    private fun processOtherLines(lines: List<String>): List<String> {
        val processedLines = mutableListOf<String>()
        var i = 0
        while (i < lines.size) {
            val line = lines[i]
            if (line.trimEnd().endsWith(":") && i + 1 < lines.size) {
                val nextLine = lines[i + 1]
                if (nextLine.trim().isNotEmpty()) {
                    val indentCount = nextLine.takeWhile { it == ' ' }.length
                    if (indentCount == 12) {
                        val fixedNextLine = "        " + nextLine.trim()
                        processedLines.add(line)
                        processedLines.add(fixedNextLine)
                        i += 2
                        continue
                    }
                }
            }
            processedLines.add(adjustIndent(line))
            i++
        }
        return processedLines
    }

    private fun adjustIndent(line: String): String {
        val indentCount = line.takeWhile { it == ' ' }.length
        if (indentCount == 0 || indentCount % 2 != 0) return line
        val newIndentCount = indentCount * 2
        val indent = " ".repeat(newIndentCount)
        val content = line.substring(indentCount)
        return indent + content
    }
}

