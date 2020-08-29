package map

/**
 * 使用链表实现的Map
 */

/**
 * 创建一个链表
 */
class LinkedMap<K : Comparable<K>, V> : Map<K, V>, Iterable<Pair<K, V>> {

    private val dummyHead = Node<K, V>()

    override var size = 0
        private set

    /**
     * 运算符重载，允许使用下标访问
     */
    override fun set(key: K, value: V) {
        put(key, value)
    }

    /**
     * 向字典树中插入键值对，键为[key]，值为[value]
     * 如果字典中键的数目发生变化，则返回true
     */
    override fun put(key: K, value: V) {
        fun put(x: Node<K, V>?): Node<K, V> {
            if (x == null) {
                size++
                return Node(key, value)
            }
            if (x.key == key) {
                x.value = value
                return x
            }
            x.next = put(x.next)
            return x
        }
        dummyHead.next = put(dummyHead.next)
    }

    /**
     * 删除键[key]，返回所对应的值
     */
    override fun remove(key: K): V? {
        var result: V? = null
        fun remove(x: Node<K, V>?): Node<K, V>? {
            if (x == null) return null
            if (x.key == key) {
                result = x.value
                size--
                return x.next
            }
            x.next = remove(x.next)
            return x
        }
        dummyHead.next = remove(dummyHead.next)
        return result
    }

    /**
     * 返回键[key]对应的值
     * 运算符重载，允许使用下标访问字典
     */
    override fun get(key: K): V? {
        fun get(x: Node<K, V>?): V? {
            if (x == null) return null
            if (x.key == key) return x.value
            return get(x.next)
        }
        return get(dummyHead.next)
    }

    /**
     * 运算符重载，允许使用in判断键[key]是否在字典中
     */
    override fun contains(key: K): Boolean {
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
    override val keys: Iterable<K>
        get() {
            val list = mutableListOf<K>()
            fun keys(x: Node<K, V>?) {
                if (x == null) return
                list.add(x.key!!)
                keys(x.next)
            }
            keys(dummyHead.next)
            return list
        }

    private class Node<K, V>(var key: K? = null, var value: V? = null, var next: Node<K, V>? = null)

    override fun iterator(): Iterator<Pair<K, V>> {
        return object : Iterator<Pair<K, V>> {
            var next: Node<K, V>? = dummyHead.next
            override fun hasNext(): Boolean {
                return next != null
            }

            override fun next(): Pair<K, V> {
                if (next == null) throw NoSuchElementException()
                val key = next!!.key!!
                val value = next!!.value!!
                next = next!!.next
                return key to value
            }
        }
    }
}
