package map

import java.util.*

/**
 * 使用三向搜索树实现的Map
 * 此实现只能使用字符串作为键值。
 * 三向搜索树可以避免Trie对空间的过度消耗
 */

/**
 * 创建一个三向字典树
 */
class TernarySearchTreeMap<V> : Map<String, V> {

    override var size = 0
        private set
    private var root = Node<V>(' ')

    /**
     * 返回键[key]对应的值
     * 运算符重载，允许使用下标访问字典
     */
    override operator fun get(key: String): V? {
        return get(root, key, 0)
    }

    private fun get(x: Node<V>, key: String, d: Int): V? {
        if (d == key.length) {
            return x.value
        } else {
            val c = key[d]
            if (c < x.c && x.left != null && x.left!!.c == c) return get(x.left!!, key, d + 1)
            if (c == x.c && x.mid != null && x.mid!!.c == c) return get(x.mid!!, key, d + 1)
            if (c > x.c && x.right != null && x.right!!.c == c) return get(x.right!!, key, d + 1)
            return null
        }
    }

    /**
     * 运算符重载，允许使用下标访问字典
     */
    override operator fun set(key: String, value: V) {
        put(key, value)
    }

    /**
     * 向字典树中插入键值对，键为[key]，值为[value]
     */
    override fun put(key: String, value: V) {
        if (key.isEmpty()) throw IllegalArgumentException("The empty key isn't be supported")
        fun put(x: Node<V>, d: Int) {
            if (d == key.length) {
                if (x.value == null) size++
                x.value = value
            } else {
                val c = key[d]
                if (c < x.c) {
                    if (x.left == null) x.left = Node(c)
                    put(x.left!!, d + 1)
                }
                if (c == x.c) {
                    if (x.mid == null) x.mid = Node(c)
                    put(x.mid!!, d + 1)
                }
                if (c > x.c) {
                    if (x.right == null) x.right = Node(c)
                    put(x.right!!, d + 1)
                }
            }
        }

        put(root, 0)
    }

    /**
     * 删除键[key]和[key]对应的值
     */
    override fun remove(key: String): V? {
        var result: V? = null
        fun remove(x: Node<V>, d: Int): Node<V>? {
            if (d == key.length) {
                x.value?.let {
                    result = it
                    x.value = null
                    size--
                }
            } else {
                val c = key[d]
                if (x.left != null && x.left!!.c == c) x.left = remove(x.left!!, d + 1)
                if (x.mid != null && x.mid!!.c == c) x.mid = remove(x.mid!!, d + 1)
                if (x.right != null && x.right!!.c == c) x.right = remove(x.right!!, d + 1)
            }
            if (x.value != null) return x
            if (x.left != null || x.mid != null || x.right != null) return x
            return null
        }
        root = remove(root, 0) ?: Node(' ')
        return result
    }

    /**
     * 运算符重载，允许使用in判断键[key]是否在字典中
     */
    override operator fun contains(key: String): Boolean {
        return get(key) != null
    }

    /**
     * 判断字典是否为空
     */
    override fun isEmpty(): Boolean {
        return size == 0
    }

    /**
     * 获取字典中所有的键
     */
    fun keys(): Iterable<String> {
        val queue = ArrayDeque<String>()
        fun collect(x: Node<V>, pre: String) {
            x.value?.let { queue.addLast(pre) }
            if (x.left != null) collect(x.left!!, pre + x.c)
            if (x.mid != null) collect(x.mid!!, pre + x.c)
            if (x.right != null) collect(x.right!!, pre + x.c)
        }
        collect(root, "")
        return queue
    }

    private class Node<V>(
        val c: Char,
        var value: V? = null,
        var left: Node<V>? = null,
        var mid: Node<V>? = null,
        var right: Node<V>? = null
    )
}