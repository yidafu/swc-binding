package dev.yidafu.swc.generator.util

import dev.yidafu.swc.generator.model.kotlin.KotlinType

object NameUtils {
    fun removeBackticks(name: String): String = name.removeSurrounding("`")

    fun addBackticks(name: String): String = if (name.startsWith("`") && name.endsWith("`")) {
        name
    } else {
        "`$name`"
    }

    fun normalized(name: String): String = removeBackticks(name)

    fun simpleNameOf(type: KotlinType): String? {
        return when (type) {
            is KotlinType.Simple -> removeBackticks(type.name)
            is KotlinType.Nullable -> simpleNameOf(type.innerType)
            else -> null
        }
    }

    fun sortNamesByNormalized(names: Collection<String>): List<String> {
        return names.sortedBy { removeBackticks(it) }
    }

    fun <V> sortMapKeysByNormalized(map: Map<String, V>): LinkedHashMap<String, V> {
        val ordered = LinkedHashMap<String, V>()
        map.keys.sortedBy { removeBackticks(it) }.forEach { k ->
            ordered[k] = map.getValue(k)
        }
        return ordered
    }
}
