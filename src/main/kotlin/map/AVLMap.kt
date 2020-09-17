package map

/**
 * 使用AVL树实现的Map
 * AVL树是最早提出的平衡二叉树。在AVL树中，任意一个节点的两棵子树最大高度差为1。
 * 因此AVL树可以保证极高的查询性能。但是在插入和删除时需要进行多次的旋转操作以保持树的平衡性。
 * 在查询较少、增删较多的场景中，红黑树是更好的选择。
 *
 * @constructor 创建一个AVL树
 */
class AVLMap<K : Comparable<K>, V> : Map<K, V> {

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
            if (x == null) return Node(key, value)
            return when {
                key < x.key -> {
                    x.left = put(x.left)
                    x.size = sizeOf(x.left) + sizeOf(x.right) + 1
                    x.height = maxOf(heightOf(x.left), heightOf(x.right)) + 1
                    balance(x)
                }
                key > x.key -> {
                    x.right = put(x.right)
                    x.size = sizeOf(x.left) + sizeOf(x.right) + 1
                    x.height = maxOf(heightOf(x.left), heightOf(x.right)) + 1
                    balance(x)
                }
                else -> {
                    result = x.value
                    x.value = value
                    x
                }
            }
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
     * 删除键[key]，返回所对应的值
     */
    override fun remove(key: K): V? {
        var result: V? = null
        fun remove(x: Node<K, V>?): Node<K, V>? {
            if (x == null) return null
            return when {
                key < x.key -> {
                    x.left = remove(x.left)
                    x.size = sizeOf(x.left) + sizeOf(x.right) + 1
                    x.height = maxOf(heightOf(x.left), heightOf(x.right)) + 1
                    balance(x)
                }
                key > x.key -> {
                    x.right = remove(x.right)
                    x.size = sizeOf(x.left) + sizeOf(x.right) + 1
                    x.height = maxOf(heightOf(x.left), heightOf(x.right)) + 1
                    balance(x)
                }
                else -> {
                    result = x.value
                    return when {
                        x.right == null -> x.left
                        x.left == null -> x.right
                        else -> {
                            val t = min(x.right)!!
                            t.right = removeMin(x.right)
                            t.left = x.left
                            t.size = sizeOf(t.left) + sizeOf(t.right) + 1
                            t.height = maxOf(heightOf(t.left), heightOf(t.right)) + 1
                            balance(t)
                        }
                    }
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

    private fun heightOf(x: Node<K, V>?): Int {
        return x?.height ?: 0
    }

    private fun balance(x: Node<K, V>): Node<K, V> {

        fun balanceFactorOf(x: Node<K, V>?): Int {
            return if (x == null) 0 else heightOf(x.left) - heightOf(x.right)
        }

        return when {
            balanceFactorOf(x) > 1 && balanceFactorOf(x.left) >= 0 -> rotateRight(x)
            balanceFactorOf(x) > 1 && balanceFactorOf(x.left) < 0 -> {
                if (x.left != null) x.left = rotateLeft(x.left!!)
                rotateRight(x)
            }
            balanceFactorOf(x) < -1 && balanceFactorOf(x.right) <= 0 -> rotateLeft(x)
            balanceFactorOf(x) < -1 && balanceFactorOf(x.right) > 0 -> {
                if (x.right != null) x.right = rotateRight(x.right!!)
                rotateLeft(x)
            }
            else -> x
        }
    }


    /*
        b                         d
       / \         左旋           / \
      a   d       ----->        b   e
         / \                   / \
        c   e                 a   c
     */
    private fun rotateLeft(b: Node<K, V>): Node<K, V> {
        val d = b.right!!
        b.right = d.left
        d.left = b
        b.size = sizeOf(b.left) + sizeOf(b.right) + 1
        d.size = sizeOf(d.left) + sizeOf(d.right) + 1
        b.height = maxOf(heightOf(b.left), heightOf(b.right)) + 1
        d.height = maxOf(heightOf(d.left), heightOf(d.right)) + 1
        return d
    }


    /*
        d                         b
       / \         右旋           / \
      b   e       ----->        a   d
     / \                           / \
    a   c                         c   e
     */
    private fun rotateRight(d: Node<K, V>): Node<K, V> {
        val b = d.left!!
        d.left = b.right
        b.right = d
        d.size = sizeOf(d.left) + sizeOf(d.right) + 1
        b.size = sizeOf(b.left) + sizeOf(b.right) + 1
        d.height = maxOf(heightOf(d.left), heightOf(d.right)) + 1
        b.height = maxOf(heightOf(b.left), heightOf(b.right)) + 1
        return b
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
        x.height = maxOf(heightOf(x.left), heightOf(x.right)) + 1
        return balance(x)
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
        x.height = maxOf(heightOf(x.left), heightOf(x.right)) + 1
        return balance(x)
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
        var height: Int = 1,
        var left: Node<K, V>? = null,
        var right: Node<K, V>? = null
    )
}