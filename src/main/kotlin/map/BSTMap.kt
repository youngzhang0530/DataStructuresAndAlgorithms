package map

/**
 * 使用二分搜索树实现的Map
 *
 * @constructor 创建一个二分搜索树
 */
class BSTMap<K : Comparable<K>, V> : Map<K, V> {

    /**
     * Map中元素的数量
     */
    override val size
        get() = sizeOf(root)

    private var root: Node<K, V>? = null

    /**
     * 运算符重载，允许使用下标访问
     */
    override operator fun set(key: K, value: V) {
        put(key, value)
    }

    /**
     * 向树中插入键值对，键为[key]，值为[value]
     */
    override fun put(key: K, value: V): V? {
        var result: V? = null
        fun put(x: Node<K, V>?): Node<K, V> {
            if (x == null) {
                return Node(key, value)
            }
            when {
                key < x.key -> {
                    x.left = put(x.left)
                    x.size = sizeOf(x.left) + sizeOf(x.right) + 1
                }
                key > x.key -> {
                    x.right = put(x.right)
                    x.size = sizeOf(x.left) + sizeOf(x.right) + 1
                }
                else -> {
                    result = x.value
                    x.value = value
                }
            }
            return x
        }

        root = put(root)
        return result
    }

    /**
     * 返回键[key]对应的值
     * 运算符重载，允许使用下标访问字典
     */
    override operator fun get(key: K): V? {
        return get(root, key)?.value
    }

    private fun get(x: Node<K, V>?, key: K): Node<K, V>? {
        if (x == null) return null
        if (key < x.key) return get(x.left, key)
        if (key > x.key) return get(x.right, key)
        return x
    }

    /**
     * 运算符重载，允许使用in判断键[key]是否在字典中
     */
    override operator fun contains(key: K): Boolean {
        return get(root, key) != null
    }

    /**
     * 判断字典是否为空
     */
    override fun isEmpty(): Boolean {
        return size == 0
    }

    private fun sizeOf(x: Node<K, V>?): Int {
        return x?.size ?: 0
    }

    /**
     * 选取排名为[k]的键
     */
    fun select(k: Int): K? {

        fun select(x: Node<K, V>?, k: Int): Node<K, V>? {
            if (x == null || k > x.size) return null
            val t = sizeOf(x.left)
            return when {
                k <= t -> select(x.left!!, k)
                k == t + 1 -> x
                else -> select(x.right!!, k - t - 1)
            }
        }
        return select(root, k)?.key
    }

    /**
     * 获得[key]的排名
     */
    fun rank(key: K): Int {

        fun rank(x: Node<K, V>?): Int {
            if (x == null) return -1
            return when {
                key < x.key -> rank(x.left)
                key > x.key -> rank(x.right) + sizeOf(x.left) + 1
                else -> sizeOf(x.left) + 1
            }
        }
        return rank(root)
    }

    /**
     * 获得最小的键
     */
    fun min(): K? {
        return min(root)?.key
    }

    private fun min(x: Node<K, V>?): Node<K, V>? {
        if (x == null) return null
        if (x.left == null) return x
        return min(x.left!!)
    }

    /**
     * 获得最大的键
     */
    fun max(): K? {
        return root?.let { max(it)?.key }
    }

    private fun max(x: Node<K, V>?): Node<K, V>? {
        if (x == null) return null
        if (x.right == null) return x
        return max(x.right!!)
    }

    /**
     * 获得小于定于[key]的最大键
     */
    fun floor(key: K): K? {

        fun floor(x: Node<K, V>?): Node<K, V>? {
            if (x == null) return null
            if (key == x.key) return x
            if (key < x.key) return floor(x.left)
            return floor(x.right) ?: x
        }

        return floor(root)?.key
    }

    /**
     * 获得大于等于[key]的最小键
     */
    fun ceiling(key: K): K? {

        fun ceiling(x: Node<K, V>?): Node<K, V>? {
            if (x == null) return null
            if (key == x.key) return x
            if (key > x.key) return ceiling(x.right)
            return ceiling(x.left) ?: x
        }

        return ceiling(root)?.key
    }

    /**
     * 删除最小键
     */
    fun removeMin() {
        root = removeMin(root)
    }

    private fun removeMin(x: Node<K, V>?): Node<K, V>? {
        if (x == null) return null
        if (x.left == null) return x.right
        x.left = removeMin(x.left)
        x.size = sizeOf(x.left) + sizeOf(x.right) + 1
        return x
    }

    /**
     * 删除最大键
     */
    fun removeMax() {
        root = removeMax(root)
    }

    private fun removeMax(x: Node<K, V>?): Node<K, V>? {
        if (x == null) return null
        if (x.right == null) return x.left
        x.right = removeMax(x.right)
        x.size = sizeOf(x.left) + sizeOf(x.right) + 1
        return x
    }

    /**
     * 删除键[key]，返回所对应的值
     */
    override fun remove(key: K): V? {
        var result: V? = null
        fun remove(x: Node<K, V>?): Node<K, V>? {
            if (x == null) return null
            when {
                key < x.key -> {
                    x.left = remove(x.left)
                    x.size = sizeOf(x.left) + sizeOf(x.right) + 1
                    return x
                }
                key > x.key -> {
                    x.right = remove(x.right)
                    x.size = sizeOf(x.left) + sizeOf(x.right) + 1
                    return x
                }
                else -> {
                    result = x.value
                    if (x.right == null) return x.left
                    if (x.left == null) return x.right
                    val t = min(x.right)
                    t!!.left = x.left
                    t.right = removeMin(x.right)
                    t.size = sizeOf(t.left) + sizeOf(t.right) + 1
                    return t
                }
            }
        }

        root = remove(root)
        return result
    }

    /**
     * 获取字典中所有的键
     */
    override val keys: Iterable<K>
        get() {
            if (root == null) return emptyList()
            return keys(min()!!, max()!!)
        }

    /**
     * 获取字典中范围在[from]到[to]之间的键
     */
    fun keys(from: K, to: K): Iterable<K> {
        val list = mutableListOf<K>()
        fun keys(x: Node<K, V>?, from: K, to: K) {
            if (x == null) return
            if (from < x.key) keys(x.left, from, x.key)
            if (x.key in from..to) list.add(x.key)
            if (x.key < to) keys(x.right, x.key, to)
        }
        keys(root, from, to)
        return list
    }

    private class Node<K : Comparable<K>, V>(
        val key: K,
        var value: V,
        var size: Int = 1,
        var left: Node<K, V>? = null,
        var right: Node<K, V>? = null
    )
}