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
    private var root: Node<V>? = null

    /**
     * 返回键[key]对应的值
     * 运算符重载，允许使用下标访问字典
     */
    override operator fun get(key: String): V? {
        return get(root, key, 0)?.value
    }

    private fun get(x: Node<V>?, key: String, d: Int): Node<V>? {
        if (x == null) return null
        if (d == key.length - 1) return x
        val c = key[d]
        return when {
            c < x.c -> get(x.left, key, d)
            c > x.c -> get(x.right, key, d)
            else -> get(x.mid, key, d + 1)
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
    override fun put(key: String, value: V): V? {
        if (key.isEmpty()) throw IllegalArgumentException("The empty key isn't be supported")
        var result: V? = null
        fun put(x: Node<V>?, d: Int): Node<V> {
            val c = key[d]
            var node = x
            if (node == null) {
                node = Node(c)
                if (d == key.length - 1) {
                    size++
                    node.value = value
                    return node
                }
            }
            when {
                c < node.c -> node.left = put(node.left, d)
                c > node.c -> node.right = put(node.right, d)
                else -> {
                    if (d == key.length - 1) {
                        result = node.value
                        node.value = value
                    } else {
                        node.mid = put(node.mid, d + 1)
                    }
                }
            }
            return node
        }

        root = put(root, 0)
        return result
    }

    /**
     * 删除键[key]和[key]对应的值
     */
    override fun remove(key: String): V? {
        TODO("Not yet implemented")
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
    override val keys: Iterable<String>
        get() {
            val queue = ArrayDeque<String>()
            fun collect(x: Node<V>?, pre: String) {
                if (x == null) return
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