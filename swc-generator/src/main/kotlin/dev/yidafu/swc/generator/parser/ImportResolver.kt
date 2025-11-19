package dev.yidafu.swc.generator.parser

import dev.yidafu.swc.generator.util.CollectionUtils
import dev.yidafu.swc.generator.util.Logger
import java.io.File
import java.nio.file.Paths

/**
 * Import resolver for TypeScript declaration files
 * Handles detection and resolution of import statements from AST
 */
class ImportResolver {

    private val visitedFiles = CollectionUtils.newStringSet()

    /**
     * Resolve import paths from AST nodes
     * @param importNodes List of import AST nodes
     * @param parentFile The file containing these imports
     * @return List of resolved absolute file paths to parse
     */
    fun resolveImports(importNodes: List<AstNode>, parentFile: String): List<String> {
        val resolvedPaths = mutableListOf<String>()
        val parentDir = File(parentFile).parentFile?.absolutePath ?: return emptyList()

        Logger.debug("Resolving imports from: $parentFile")
        Logger.debug("Parent directory: $parentDir")

        for (importNode in importNodes) {
            val sourcePath = extractImportSource(importNode)
            if (sourcePath != null) {
                val resolvedPath = resolveImportPath(sourcePath, parentDir)
                if (resolvedPath != null && !visitedFiles.contains(resolvedPath)) {
                    resolvedPaths.add(resolvedPath)
                    visitedFiles.add(resolvedPath)
                    Logger.debug("Resolved import: $sourcePath -> $resolvedPath")
                } else if (visitedFiles.contains(resolvedPath)) {
                    Logger.debug("Skipping already visited file: $resolvedPath")
                }
            }
        }

        return resolvedPaths
    }

    /**
     * Extract import source path from AST node
     */
    private fun extractImportSource(importNode: AstNode): String? {
        return when (importNode.type) {
            "ImportDeclaration" -> {
                val source = importNode.getNode("source")
                source?.getString("value")
            }
            "TsImportDeclaration" -> {
                val source = importNode.getNode("source")
                source?.getString("value")
            }
            else -> {
                Logger.debug("Unknown import type: ${importNode.type}")
                null
            }
        }
    }

    /**
     * Resolve relative import path to absolute file path
     */
    private fun resolveImportPath(sourcePath: String, parentDir: String): String? {
        return try {
            // Handle relative imports (e.g., "./assumptions" -> "assumptions.d.ts")
            if (sourcePath.startsWith("./") || sourcePath.startsWith("../")) {
                val resolvedPath = Paths.get(parentDir, sourcePath).normalize()
                val candidateFiles = listOf(
                    "$resolvedPath.d.ts",
                    "$resolvedPath/index.d.ts",
                    resolvedPath.toString()
                )

                candidateFiles.find { File(it).exists() }
            } else {
                // Handle absolute or node_modules imports
                // For now, skip non-relative imports as they're likely external dependencies
                Logger.debug("Skipping non-relative import: $sourcePath")
                null
            }
        } catch (e: Exception) {
            Logger.debug("Error resolving import path '$sourcePath': ${e.message}")
            null
        }
    }

    /**
     * Check if a file has been visited to prevent circular imports
     */
    fun isVisited(filePath: String): Boolean {
        return visitedFiles.contains(filePath)
    }

    /**
     * Mark a file as visited
     */
    fun markVisited(filePath: String) {
        visitedFiles.add(filePath)
    }

    /**
     * Clear visited files (useful for testing)
     */
    fun clearVisited() {
        visitedFiles.clear()
    }
}
