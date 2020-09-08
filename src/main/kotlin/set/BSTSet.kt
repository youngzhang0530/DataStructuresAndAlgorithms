package set

import map.BSTMap

/**
 * 基于二分搜索树的集合
 *
 * @constructor 创建一个集合
 */
class BSTSet<E : Comparable<E>> : Set<E> {
    private companion object {
        val PRESENT = Any()
    }

    private val map = BSTMap<E, Any>()

    /**
     * 集合中元素的数量
     */
    override val size: Int
        get() = map.size

    /**
     * 向集合中添加元素[e]。如果集合中不存在[e]，则返回true
     */
    override fun add(e: E): Boolean {
        return map.put(e, PRESENT) == null
    }

    /**
     * 从集合中移除元素[e]。如果集合中存在[e]，则返回true
     */
    override fun remove(e: E): Boolean {
        return map.remove(e) != null
    }

    /**
     * 判断集合是否为空
     */
    override fun isEmpty(): Boolean {
        return map.isEmpty()
    }

    /**
     * 判断集合中是否包含元素[e]
     */
    override fun contains(e: E): Boolean {
        return map.contains(e)
    }
}