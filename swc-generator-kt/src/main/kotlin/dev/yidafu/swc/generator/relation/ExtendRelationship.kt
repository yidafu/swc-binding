package dev.yidafu.swc.generator.relation

/**
 * 类型继承关系管理
 */
object ExtendRelationship {
    private val child2ParentMap = mutableMapOf<String, MutableSet<String>>()
    private val parent2ChildMap = mutableMapOf<String, MutableSet<String>>()
    private val nodeExtendsList = mutableListOf<Pair<String, String>>()
    
    /**
     * 添加继承关系
     * @param parent 父类型
     * @param child 子类型
     */
    fun addRelation(parent: String, child: String) {
        val pSet = child2ParentMap.getOrPut(child) { mutableSetOf() }
        pSet.add(parent)
        
        val cSet = parent2ChildMap.getOrPut(parent) { mutableSetOf() }
        cSet.add(child)
        
        nodeExtendsList.add(parent to child)
    }
    
    /**
     * 判断是否为叶子节点（没有子类型）
     */
    fun isGrandChild(child: String): Boolean {
        return findAllChildrenByParent(child).isEmpty()
    }
    
    /**
     * 获取根类型
     */
    fun getRoot(child: String): String? {
        val parentList = child2ParentMap[child]
        if (parentList != null && parentList.isNotEmpty()) {
            for (type in parentList) {
                val root = getRoot(type)
                if (root != null) return root
            }
            return null
        } else {
            return child
        }
    }
    
    /**
     * 查找直接父类型
     */
    fun findParentsByChild(child: String): List<String> {
        return child2ParentMap[child]?.toList() ?: emptyList()
    }
    
    /**
     * 查找所有父类型（递归）
     */
    fun findAllParentByChild(child: String): List<String> {
        val result = mutableSetOf<String>()
        findParentsByChild(child).forEach { parent ->
            result.add(parent)
            result.addAll(findAllParentByChild(parent))
        }
        return result.toList()
    }
    
    /**
     * 获取所有父类型
     */
    fun getAllParents(): List<String> {
        return parent2ChildMap.keys.toList()
    }
    
    /**
     * 获取所有子类型
     */
    fun getAllChildren(): List<String> {
        return child2ParentMap.keys.toList()
    }
    
    /**
     * 查找所有叶子子类型（递归）
     */
    fun findAllGrandChildren(type: String): List<String> {
        val result = mutableSetOf<String>()
        val childTypes = nodeExtendsList.filter { it.first == type }.map { it.second }
        
        if (childTypes.isNotEmpty()) {
            childTypes.forEach { child ->
                result.addAll(findAllGrandChildren(child))
            }
        } else {
            result.add(type)
        }
        
        return result.toList()
    }
    
    /**
     * 查找直接子类型
     */
    fun findAllDirectChildren(type: String): List<String> {
        return parent2ChildMap[type]?.toList() ?: emptyList()
    }
    
    /**
     * 查找所有子类型（包括间接子类型）
     */
    fun findAllChildrenByParent(type: String): List<String> {
        val result = mutableSetOf<String>()
        val childTypes = nodeExtendsList.filter { it.first == type }.map { it.second }
        result.addAll(childTypes)
        
        if (childTypes.isNotEmpty()) {
            childTypes.forEach { child ->
                result.addAll(findAllChildrenByParent(child))
            }
        }
        
        return result.toList()
    }
    
    /**
     * 获取所有类型
     */
    fun getAll(): List<String> {
        val allTypes = mutableSetOf<String>()
        allTypes.addAll(child2ParentMap.keys)
        allTypes.addAll(parent2ChildMap.keys)
        return allTypes.toList()
    }
    
    /**
     * 查找与指定节点相关的所有类型
     */
    fun findAllRelativeByName(node: String): List<String> {
        val children = findAllGrandChildren(node)
        val result = mutableSetOf<String>()
        children.forEach { child ->
            result.add(child)
            result.addAll(findAllParentByChild(child))
        }
        return result.toList()
    }
    
    /**
     * 查找共同父类型
     */
    fun getCommonParent(children: List<String>): String {
        val child2ParentPathMap = mutableMapOf<String, List<String>>()
        children.forEach { child ->
            child2ParentPathMap[child] = getPathWithFound(child)
        }
        
        val parentPathList = child2ParentPathMap.values.toList()
        val distanceMap = mutableMapOf<String, Int>()
        val allParentTypes = parentPathList.flatten().toSet()
        
        allParentTypes.forEach { pType ->
            var maxDistance = -1
            parentPathList.forEach { list ->
                maxDistance = maxOf(maxDistance, list.indexOf(pType))
            }
            distanceMap[pType] = maxDistance
        }
        
        var commonType = "Node"
        var minDistance = 999
        distanceMap.entries.forEach { (k, v) ->
            if (v <= minDistance) {
                commonType = k
                minDistance = v
            }
        }
        
        return commonType
    }
    
    /**
     * 获取到根节点的路径
     */
    fun getPathWithFound(child: String): List<String> {
        val path2Node = (getPathWithOrder("Node", child) ?: listOf(child)).toMutableSet()
        val pSet = child2ParentMap[child] ?: mutableSetOf()
        pSet.forEach { p ->
            if (!path2Node.contains(p) && p != "HasSpan") {
                path2Node.add(p)
            }
        }
        return path2Node.toList()
    }
    
    /**
     * 获取有序路径
     */
    private fun getPathWithOrder(node: String, child: String): List<String>? {
        val cSet = parent2ChildMap[node] ?: mutableSetOf()
        if (cSet.contains(child)) {
            return listOf(child, node)
        } else {
            for (c in cSet) {
                val res = getPathWithOrder(c, child)
                if (res != null) {
                    return listOf(c) + res
                }
            }
        }
        return null
    }
    
    /**
     * 清空所有关系（用于测试）
     */
    fun clear() {
        child2ParentMap.clear()
        parent2ChildMap.clear()
        nodeExtendsList.clear()
    }
}

