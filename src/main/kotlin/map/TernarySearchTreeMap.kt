package map

/**
 * 使用三向单词搜索树实现的Map
 * 此实现只能使用字符串作为键值。
 * 三向单词搜索树可以避免Trie对空间的过度消耗
 */

/**
 * 创建一个三向单词搜索树
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
        if (key.isEmpty()) throw IllegalArgumentException("The empty key isn't be supported")
        if (x == null) return null
        val c = key[d]
        return when {
            c < x.c -> get(x.left, key, d)
            c > x.c -> get(x.right, key, d)
            else -> {
                if (d == key.length - 1) x
                else get(x.mid, key, d + 1)
            }
        }
    }

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
        if (key.isEmpty()) throw IllegalArgumentException("The empty key isn't be supported")
        var result: V? = null
        fun remove(x: Node<V>?, d: Int): Node<V>? {
            if (x == null) return null
            if (d == key.length - 1) {
                if (x.value != null) size--
                result = x.value
                x.value = null
                return when {
                    x.mid != null -> x
                    x.left == null -> x.right
                    x.right == null -> x.left
                    else -> x
                }
            }
            val c = key[d]
            when {
                c < x.c -> x.left = remove(x.left, d)
                c > x.c -> x.right = remove(x.right, d)
                else -> x.mid = remove(x.mid, d + 1)
            }
            if (x.value == null && x.left == null && x.mid == null && x.right == null) return null
            return x
        }
        root = remove(root, 0)
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
    override val keys: Iterable<String>
        get() {
            val list = mutableListOf<String>()
            fun collect(x: Node<V>?, pre: String) {
                if (x == null) return
                x.value?.let { list.add(pre) }
                if (x.left != null) collect(x.left!!, pre + x.c)
                if (x.mid != null) collect(x.mid!!, pre + x.c)
                if (x.right != null) collect(x.right!!, pre + x.c)
            }
            collect(root, "")
            return list
        }

    /**
     * 查找前缀为[s]的键
     */
    fun keysWithPrefix(s: String): Iterable<String> {
        val list = mutableListOf<String>()

        fun collect(x: Node<V>, pre: String) {
            x.value?.let { list.add(pre) }
            if (x.left != null) collect(x.left!!, pre)
            if (x.mid != null) collect(x.mid!!, pre + x.mid!!.c)
            if (x.right != null) collect(x.right!!, pre)
        }

        val node = get(root, s, 0)
        if (node != null) {
            if (node.value != null) list.add(s)
            if (node.mid != null) collect(node.mid!!, s)
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
            val d = pat[pre.length]
            if (pre.length == pat.length - 1) {
                when {
                    d == '.' -> {
                        x.value?.let { list.add(pre + x.c) }
                        if (x.left != null) collect(x.left!!, pre)
                        if (x.right != null) collect(x.right!!, pre)
                    }
                    d < x.c -> if (x.left != null) collect(x.left!!, pre)
                    d > x.c -> if (x.right != null) collect(x.right!!, pre)
                    d == x.c -> x.value?.let { list.add(pre + x.c) }
                }
            } else {
                if (d == '.') {
                    if (x.left != null) collect(x.left!!, pre)
                    if (x.mid != null) collect(x.mid!!, pre + x.c)
                    if (x.right != null) collect(x.right!!, pre)
                } else if (d == x.c) collect(x.mid!!, pre + x.c)
            }
        }

        root?.let { collect(it, "") }
        return list
    }

    /**
     * 查找[s]的前缀中最长的键
     */
    fun longestPrefixOf(s: String): String {
        if (s.isEmpty()) throw IllegalArgumentException("The empty key isn't be supported")
        var length = 0
        fun search(x: Node<V>, d: Int) {
            val c = s[d]
            if (d < s.length) {
                when {
                    c < x.c -> if (x.left != null) search(x.left!!, d)
                    c > x.c -> if (x.right != null) search(x.right!!, d)
                    else -> {
                        if (x.value != null) length = d + 1
                        if (d < s.length - 1 && x.mid != null) search(x.mid!!, d + 1)
                    }
                }
            }
        }
        root?.let { search(it, 0) }
        return s.substring(0, length)
    }

    private class Node<V>(
        val c: Char,
        var value: V? = null,
        var left: Node<V>? = null,
        var mid: Node<V>? = null,
        var right: Node<V>? = null
    )
}