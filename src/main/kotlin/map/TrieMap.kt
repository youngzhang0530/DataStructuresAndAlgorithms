package map

/**
 * 单词搜索树
 * 此实现只能使用字符串作为键值
 *
 * @constructor 创建一个单词搜索树
 */
class TrieMap<V> : Map<String, V> {

    private companion object {
        const val R = 256
    }

    private var root = Node<V>()

    /**
     * Map中元素的数量
     */
    override var size = 0
        private set

    /**
     * 运算符重载，允许使用下标访问字典
     */
    override operator fun set(key: String, value: V) {
        put(key, value)
    }

    /**
     * 向树中插入键值对，键为[key]，值为[value]
     */
    override fun put(key: String, value: V): V? {
        if (key.isEmpty()) throw IllegalArgumentException("The empty key isn't be supported")
        var result: V? = null
        fun put(x: Node<V>, d: Int) {
            if (d == key.length) {
                if (x.value == null) {
                    size++
                }
                result = x.value
                x.value = value
                return
            } else {
                val c = key[d].toInt()
                if (x.next[c] == null) x.next[c] = Node()
                put(x.next[c]!!, d + 1)
            }
        }
        put(root, 0)
        return result
    }

    /**
     * 返回键[key]对应的值
     * 运算符重载，允许使用下标访问字典
     */
    override operator fun get(key: String): V? {
        if (key.isEmpty()) throw IllegalArgumentException("The empty key isn't be supported")
        return get(root, key, 0)?.value
    }

    private fun get(x: Node<V>, key: String, d: Int): Node<V>? {
        if (d == key.length) {
            return x
        } else {
            val c = key[d].toInt()
            if (x.next[c] == null) return null
            return get(x.next[c]!!, key, d + 1)
        }
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
     * 查找前缀为[s]的键
     */
    fun keysWithPrefix(s: String): Iterable<String> {
        val list = mutableListOf<String>()

        fun collect(x: Node<V>, pre: String) {
            x.value?.let { list.add(pre) }
            for (c in 0 until R) {
                x.next[c]?.let { collect(it, pre + c.toChar()) }
            }
        }
        get(root, s, 0)?.let {
            collect(it, s)
        }
        return list
    }

    /**
     * 查找与[pat]匹配的键
     */
    fun keysThatMatch(pat: String): Iterable<String> {
        if (pat.isEmpty()) throw IllegalArgumentException("The empty key isn't be supported")
        val list = mutableListOf<String>()

        fun collect(x: Node<V>, pre: String) {
            if (pre.length == pat.length) {
                x.value?.let { list.add(pre) }
            } else {
                val d = pat[pre.length]
                for (c in 0 until R) {
                    if ((d == '.' || d == c.toChar()) && x.next[c] != null) {
                        collect(x.next[c]!!, pre + c.toChar())
                    }
                }
            }
        }

        collect(root, "")
        return list
    }

    /**
     * 查找[s]的前缀中最长的键
     */
    fun longestPrefixOf(s: String): String {
        if (s.isEmpty()) throw IllegalArgumentException("The empty key isn't be supported")
        var length = 0
        fun search(x: Node<V>, d: Int) {
            val c = s[d].toInt()
            x.next[c]?.let {
                length = d + 1
                if (d < s.length) search(x.next[c]!!, length)
            }
        }
        search(root, 0)
        return s.substring(0, length)
    }

    /**
     * 删除键[key]和[key]对应的值
     */
    override fun remove(key: String): V? {
        if (key.isEmpty()) throw IllegalArgumentException("The empty key isn't be supported")
        var result: V? = null
        fun remove(x: Node<V>, d: Int): Node<V>? {
            if (d == key.length) {
                x.value?.let {
                    result = it
                    x.value = null
                    size--
                }
            } else {
                val c = key[d].toInt()
                if (x.next[c] != null) x.next[c] = remove(x.next[c]!!, d + 1)
            }
            if (x.value != null) return x
            for (r in 0 until R) {
                if (x.next[r] != null) return x
            }
            return null
        }
        root = remove(root, 0) ?: Node()
        return result
    }

    /**
     * 获取树中所有的键
     */
    override val keys: Iterable<String>
        get() = keysWithPrefix("")

    private class Node<V>(var value: V? = null, var next: Array<Node<V>?> = arrayOfNulls(R))
}
