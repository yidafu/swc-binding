package dev.yidafu.swc.util

import kotlinx.serialization.json.*

/**
 * Utility class for comparing JSON objects ignoring property order.
 * Provides deep comparison of JSON structures.
 */
object JsonComparator {
    /**
     * Fields to ignore when comparing JSON.
     * Removed 'span' from here since we now need to compare it (but ignore ctxt within it).
     */
    private val ignoredFields = setOf<String>()
    
    /**
     * Fields to ignore when comparing specific nested objects.
     * For 'span' objects, ignore the 'ctxt' field since it's serialized differently
     * in Rust (as a separate field on the node) vs Kotlin (inside the Span).
     * The 'ctxt' field in span is redundant with the node-level 'ctxt' field.
     *
     * For 'start' and 'end' fields, we only check existence, not specific values.
     */
    private val spanIgnoredFields = setOf("ctxt", "start", "end")

    /**
     * Compare two JSON strings, ignoring property order.
     * * @param json1 First JSON string
     * @param json2 Second JSON string
     * @return true if JSON objects are equivalent (ignoring order), false otherwise
     */
    fun compare(json1: String, json2: String): Boolean {
        return try {
            val element1 = Json.parseToJsonElement(json1)
            val element2 = Json.parseToJsonElement(json2)
            val result = compareElements(element1, element2)
            if (!result) {
                println("[JsonComparator] compare returned false")
                // Find first difference
                val diffs = getDifferences(json1, json2)
                println("[JsonComparator] getDifferences returned ${diffs.size} differences")
                diffs.take(3).forEach { println("  - $it") }
            }
            result
        } catch (e: Exception) {
            println("[JsonComparator] Exception: ${e.message}")
            false
        }
    }

    /**
     * Compare two JsonElement objects recursively.
     */
    private fun compareElements(e1: JsonElement, e2: JsonElement): Boolean {
        return compareElementsWithContext(e1, e2, "")
    }

    /**
     * Compare two JSON primitives.
     */
    fun comparePrimitives(p1: JsonPrimitive, p2: JsonPrimitive): Boolean {
        // Compare as strings first, then try numeric comparison
        if (p1.isString && p2.isString) {
            return p1.content == p2.content
        }

        // For numbers, compare both as double and as long
        if (p1.isString || p2.isString) {
            return false
        }

        // Try numeric comparison
        val d1 = p1.doubleOrNull
        val d2 = p2.doubleOrNull
        if (d1 != null && d2 != null) {
            // Use epsilon for floating point comparison
            return kotlin.math.abs(d1 - d2) < 1e-10
        }

        // Try boolean comparison
        val b1 = p1.booleanOrNull
        val b2 = p2.booleanOrNull
        if (b1 != null && b2 != null) {
            return b1 == b2
        }

        return false
    }

    /**
     * Compare two JSON arrays.
     * Arrays must have the same elements in the same order.
     */
    private fun compareArrays(a1: JsonArray, a2: JsonArray): Boolean {
        if (a1.size != a2.size) {
            return false
        }
        return a1.zip(a2).all { (e1, e2) -> compareElements(e1, e2) }
    }

    /**
     * Compare two JSON objects, ignoring property order.
     */
    private fun compareObjects(o1: JsonObject, o2: JsonObject, parentKey: String = ""): Boolean {
        // Handle span objects specially - check existence of start/end but not their values
        if (parentKey == "span") {
            return compareSpanObjects(o1, o2)
        }

        // Determine which fields to ignore based on parent context
        val fieldsToIgnore = ignoredFields

        // Count non-ignored keys for size comparison
        val o1Keys = o1.keys.filter { it !in fieldsToIgnore }
        val o2Keys = o2.keys.filter { it !in fieldsToIgnore }
        if (o1Keys.size != o2Keys.size) {
            return false
        }

        // Check all keys in o1 exist in o2 with same values
        for (key in o1.keys) {
            // Skip ignored fields
            if (key in fieldsToIgnore) {
                continue
            }

            val v1 = o1[key]
            val v2 = o2[key]

            if (v2 == null) {
                // Key doesn't exist in o2
                // Missing keys are equivalent to null in JSON comparison
                // If v1 is not null and not JsonNull, they differ
                if (v1 != null && v1 !is JsonNull) {
                    return false
                }
                continue
            }

            if (v1 == null) {
                // v1 is null but v2 is not
                // Missing keys are equivalent to null in JSON comparison
                // If v2 is not null and not JsonNull, they differ
                if (v2 !is JsonNull) {
                    return false
                }
                continue
            }

            if (!compareElementsWithContext(v1, v2, key)) {
                return false
            }
        }

        // Check all keys in o2 exist in o1 (already checked above, but verify)
        for (key in o2.keys) {
            // Skip ignored fields
            if (key in fieldsToIgnore) {
                continue
            }

            if (!o1.containsKey(key)) {
                val v2 = o2[key]
                // Missing keys are equivalent to null in JSON comparison
                // Only fail if v2 is not null and not JsonNull
                if (v2 != null && v2 !is JsonNull) {
                    return false
                }
            }
        }

        return true
    }

    /**
     * Compare span objects specially:
     * - Ignore 'ctxt' field completely
     * - For 'start' and 'end' fields, only check if they exist (not their values)
     */
    private fun compareSpanObjects(o1: JsonObject, o2: JsonObject): Boolean {
        val requiredSpanFields = setOf("start", "end")
        val fieldsToIgnore = spanIgnoredFields

        // Check that both spans have required fields (start, end)
        for (requiredField in requiredSpanFields) {
            val hasField1 = o1.containsKey(requiredField)
            val hasField2 = o2.containsKey(requiredField)

            // Both must have the field or both must not have it
            if (hasField1 != hasField2) {
                return false
            }

            // If they both have the field, check that it's not null/undefined
            if (hasField1) {
                val v1 = o1[requiredField]
                val v2 = o2[requiredField]

                // Both should be non-null
                if (v1 == null || v1 is JsonNull || v2 == null || v2 is JsonNull) {
                    return false
                }
            }
        }

        // Count non-ignored, non-required fields for regular comparison
        val o1Keys = o1.keys.filter { it !in fieldsToIgnore && it !in requiredSpanFields }
        val o2Keys = o2.keys.filter { it !in fieldsToIgnore && it !in requiredSpanFields }
        if (o1Keys.size != o2Keys.size) {
            return false
        }

        // Compare all other fields normally
        for (key in o1.keys) {
            // Skip ignored fields and special handled span fields
            if (key in fieldsToIgnore || key in requiredSpanFields) {
                continue
            }

            val v1 = o1[key]
            val v2 = o2[key]

            if (v2 == null) {
                if (v1 != null && v1 !is JsonNull) {
                    return false
                }
                continue
            }

            if (v1 == null) {
                if (v2 !is JsonNull) {
                    return false
                }
                continue
            }

            if (!compareElementsWithContext(v1, v2, key)) {
                return false
            }
        }

        // Check all keys in o2 exist in o1 for non-ignored, non-required fields
        for (key in o2.keys) {
            if (key in fieldsToIgnore || key in requiredSpanFields) {
                continue
            }

            if (!o1.containsKey(key)) {
                val v2 = o2[key]
                if (v2 != null && v2 !is JsonNull) {
                    return false
                }
            }
        }

        return true
    }
    
    /**
     * Compare elements with context awareness (to handle span.ctxt specially).
     */
    private fun compareElementsWithContext(e1: JsonElement, e2: JsonElement, parentKey: String = ""): Boolean {
        return when {
            e1 is JsonNull && e2 is JsonNull -> true
            e1 is JsonPrimitive && e2 is JsonPrimitive -> comparePrimitives(e1, e2)
            e1 is JsonArray && e2 is JsonArray -> compareArrays(e1, e2)
            e1 is JsonObject && e2 is JsonObject -> compareObjects(e1, e2, parentKey)
            else -> false
        }
    }

    /**
     * Get detailed difference report between two JSON strings.
     * * @param json1 First JSON string
     * @param json2 Second JSON string
     * @return List of difference messages, empty if identical
     */
    fun getDifferences(json1: String, json2: String): List<String> {
        val differences = mutableListOf<String>()

        try {
            val element1 = Json.parseToJsonElement(json1)
            val element2 = Json.parseToJsonElement(json2)
            compareElementsWithPath(element1, element2, "", "", differences)
        } catch (e: Exception) {
            differences.add("Error parsing JSON: ${e.message}")
        }

        return differences
    }

    /**
     * Compare elements and collect differences with path information.
     */
    private fun compareElementsWithPath(
        e1: JsonElement,
        e2: JsonElement,
        path: String,
        parentKey: String,
        differences: MutableList<String>
    ) {
        when {
            e1 is JsonNull && e2 is JsonNull -> {
                // Both null, identical
            }
            e1 is JsonNull -> {
                differences.add("$path: first is null, second is ${e2::class.simpleName}")
            }
            e2 is JsonNull -> {
                differences.add("$path: first is ${e1::class.simpleName}, second is null")
            }
            e1 is JsonPrimitive && e2 is JsonPrimitive -> {
                if (!comparePrimitives(e1, e2)) {
                    differences.add("$path: values differ - '$e1' vs '$e2'")
                }
            }
            e1 is JsonArray && e2 is JsonArray -> {
                if (e1.size != e2.size) {
                    differences.add("$path: array sizes differ - ${e1.size} vs ${e2.size}")
                } else {
                    e1.zip(e2).forEachIndexed { index, (item1, item2) ->
                        compareElementsWithPath(item1, item2, "$path[$index]", "", differences)
                    }
                }
            }
            e1 is JsonObject && e2 is JsonObject -> {
                // Handle span objects specially - check existence of start/end but not their values
                if (parentKey == "span") {
                    compareSpanElementsWithPath(e1, e2, path, differences)
                    return
                }

                // Determine which fields to ignore based on parent context
                // Use parentKey instead of path for consistency with compare() method
                val fieldsToIgnore = ignoredFields

                val allKeys = (e1.keys + e2.keys).distinct()
                for (key in allKeys) {
                    // Skip ignored fields
                    if (key in fieldsToIgnore) {
                        continue
                    }

                    val v1 = e1[key]
                    val v2 = e2[key]
                    val newPath = if (path.isEmpty()) key else "$path.$key"

                    when {
                        v1 == null && v2 == null -> {
                            // Both missing, skip
                        }
                        v1 == null -> {
                            // Missing in first JSON, only report if v2 is not null
                            if (v2 !is JsonNull) {
                                differences.add("$newPath: missing in first JSON (present in second: $v2)")
                            }
                        }
                        v2 == null -> {
                            // Missing in second JSON, only report if v1 is not null
                            if (v1 !is JsonNull) {
                                differences.add("$newPath: missing in second JSON (present in first: $v1)")
                            }
                        }
                        else -> {
                            compareElementsWithPath(v1, v2, newPath, key, differences)
                        }
                    }
                }
            }
            else -> {
                differences.add("$path: type mismatch - ${e1::class.simpleName} vs ${e2::class.simpleName}")
            }
        }
    }

    /**
     * Compare span objects and collect differences, with special handling for start/end fields.
     */
    private fun compareSpanElementsWithPath(
        e1: JsonObject,
        e2: JsonObject,
        path: String,
        differences: MutableList<String>
    ) {
        val requiredSpanFields = setOf("start", "end")
        val fieldsToIgnore = spanIgnoredFields

        // Check existence of required span fields
        for (requiredField in requiredSpanFields) {
            val hasField1 = e1.containsKey(requiredField)
            val hasField2 = e2.containsKey(requiredField)

            if (hasField1 != hasField2) {
                val fieldPath = if (path.isEmpty()) requiredField else "$path.$requiredField"
                if (hasField1) {
                    differences.add("$fieldPath: present in first span, missing in second span")
                } else {
                    differences.add("$fieldPath: present in second span, missing in first span")
                }
                continue
            }

            // If both have the field, check for null values (but don't compare actual values)
            if (hasField1) {
                val v1 = e1[requiredField]
                val v2 = e2[requiredField]
                val fieldPath = if (path.isEmpty()) requiredField else "$path.$requiredField"

                if (v1 == null || v1 is JsonNull) {
                    differences.add("$fieldPath: null/undefined in first span")
                }
                if (v2 == null || v2 is JsonNull) {
                    differences.add("$fieldPath: null/undefined in second span")
                }
            }
        }

        // Compare all other fields normally (except ignored ones)
        val allKeys = (e1.keys + e2.keys).distinct()
        for (key in allKeys) {
            // Skip ignored fields and special handled span fields
            if (key in fieldsToIgnore || key in requiredSpanFields) {
                continue
            }

            val v1 = e1[key]
            val v2 = e2[key]
            val newPath = if (path.isEmpty()) key else "$path.$key"

            when {
                v1 == null && v2 == null -> {
                    // Both missing, skip
                }
                v1 == null -> {
                    // Missing in first JSON, only report if v2 is not null
                    if (v2 !is JsonNull) {
                        differences.add("$newPath: missing in first span (present in second: $v2)")
                    }
                }
                v2 == null -> {
                    // Missing in second JSON, only report if v1 is not null
                    if (v1 !is JsonNull) {
                        differences.add("$newPath: missing in second span (present in first: $v1)")
                    }
                }
                else -> {
                    compareElementsWithPath(v1, v2, newPath, key, differences)
                }
            }
        }
    }
}
