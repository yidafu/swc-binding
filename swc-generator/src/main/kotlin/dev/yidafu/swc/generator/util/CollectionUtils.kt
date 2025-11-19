package dev.yidafu.swc.generator.util

/**
 * 集合工具类
 * * 提供统一的集合初始化方法，减少代码重复，提高代码可读性和可维护性。
 * 所有集合初始化都通过此类进行，便于后续统一优化和管理。
 * * @since 1.0.0
 */
object CollectionUtils {
    /**
     * 创建新的字符串集合
     * * 统一集合初始化模式，提高代码可读性。
     * 用于存储字符串集合的场景，如属性名集合、已访问文件集合等。
     * * @return 新的可变字符串集合
     */
    fun newStringSet(): MutableSet<String> = mutableSetOf()

    /**
     * 创建新的字符串映射
     * * 统一映射初始化模式，提高代码可读性。
     * 用于存储键为字符串的映射关系。
     * * @param V 映射值的类型
     * @return 新的可变字符串映射
     */
    fun <V> newStringMap(): MutableMap<String, V> = mutableMapOf()

    /**
     * 创建新的字符串到字符串集合的映射
     * * 用于存储父子关系等场景，如接口继承关系、类型别名关系等。
     * * @return 新的可变映射，键为字符串，值为字符串集合
     */
    fun newStringToSetMap(): MutableMap<String, MutableSet<String>> = mutableMapOf()
}
