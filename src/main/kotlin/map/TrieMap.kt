package map

import java.util.*

/**
 * 使用Trie实现的Map
 * 此实现只能使用字符串作为键值
 */

/**
 * 创建一个字典树
 */
class TrieMap<V> {

    private companion object {
        const val R = 256
    }

    private var root = Node<V>()
    var size = 0
        private set

    /**
     * 运算符重载，允许使用下标访问字典
     */
    operator fun set(k: String, v: V) {
        put(k, v)
    }

    /**
     * 向字典树中插入键值对，键为[k]，值为[v]
     */
    fun put(k: String, v: V) {
        if (k.isBlank()) throw IllegalArgumentException("The blank key isn't supported")
        fun put(x: Node<V>, d: Int) {
            if (d < k.length) {
                val c = k[d].toInt()
                if (x.next[c] == null) x.next[c] = Node()
                if (d == k.length - 1) {
                    if (x.next[c]!!.value == null) size++
                    x.next[c]!!.value = v
                } else {
                    put(x.next[c]!!, d + 1)
                }
            }
        }

        put(root, 0)
    }

    /**
     * 返回键[k]对应的值
     * 运算符重载，允许使用下标访问字典
     */
    operator fun get(k: String): V? {
        return get(root, k, 0)?.value
    }

    private fun get(x: Node<V>, k: String, d: Int): Node<V>? {
        if (d < k.length) {
            val c = k[d].toInt()
            if (x.next[c] == null) return null
            if (d == k.length - 1) return x.next[c]
            return get(x.next[c]!!, k, d + 1)
        }
        return root
    }

    /**
     * 运算符重载，允许使用in判断键[k]是否在字典中
     */
    operator fun contains(k: String): Boolean {
        return get(k) != null
    }


    /**
     * 判断字典是否为空
     */
    fun isEmpty(): Boolean {
        return size == 0
    }

    /**
     * 查找前缀为[s]的键
     */
    fun keysWithPrefix(s: String): Iterable<String> {
        val queue = ArrayDeque<String>()

        fun collect(x: Node<V>, pre: String) {
            x.value?.let { queue.addLast(pre) }
            for (c in 0 until R) {
                x.next[c]?.let { collect(it, pre + c.toChar()) }
            }
        }
        get(root, s, 0)?.let {
            collect(it, s)
        }
        return queue
    }

    /**
     * 查找与[pat]匹配的键
     */
    fun keysThatMatch(pat: String): Iterable<String> {
        val queue = ArrayDeque<String>()

        fun collect(x: Node<V>, pre: String) {
            if (pre.length == pat.length) {
                x.value?.let { queue.addLast(pre) }
            } else if (pre.length < pat.length) {
                val d = pat[pre.length]
                for (c in 0 until R) {
                    if ((d == '.' || d == c.toChar()) && x.next[c] != null) {
                        collect(x.next[c]!!, pre + c.toChar())
                    }
                }
            }
        }

        collect(root, "")
        return queue
    }

    /**
     * 查找[s]的前缀中最长的键
     */
    fun longestPrefixOf(s: String): String {
        var length = 0
        fun search(x: Node<V>, d: Int) {
            if (d < s.length) {
                val c = s[d].toInt()
                x.next[c]?.let {
                    length = d + 1
                    if (d < s.length) search(x.next[c]!!, length)
                }
            }
        }
        search(root, 0)
        return s.substring(0, length)
    }

    /**
     * 删除键[k]和[k]对应的值
     */
    fun remove(k: String): V? {
        var result: V? = null
        fun remove(x: Node<V>, d: Int): Node<V>? {
            if (d == k.length) {
                x.value?.let {
                    result = it
                    x.value = null
                    size--
                }
            } else {
                val c = k[d].toInt()
                if (x.next[c] == null) return x
                x.next[c] = remove(x.next[c]!!, d + 1)
                if (x.value != null) return x
            }
            for (r in 0 until R) {
                if (x.next[r] != null) return x
            }
            return null
        }
        root = remove(root, 0) ?: Node()
        return result
    }

    /**
     * 获取字典中所有的键
     */
    fun keys(): Iterable<String> {
        return keysWithPrefix("")
    }

    private class Node<V>(var value: V? = null, var next: Array<Node<V>?> = arrayOfNulls(R))
}
