package map

/**
 * 基于拉链法的哈希表
 *
 * @constructor 创建一个Map
 */
class SeparateChainingHashMap<K : Comparable<K>, V> : Map<K, V> {
    private companion object {
        const val DEFAULT_CAPACITY = 1 shl 4
    }

    private var capacity = DEFAULT_CAPACITY

    /**
     * Map中元素的数量
     */
    override var size = 0
        private set
    private var table = Array(capacity) { LinkedMap<K, V>() }

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
    override fun put(key: K, value: V): V? {
        if (size == 8 * capacity) resize(capacity shl 1)
        if (!table[hash(key)].contains(key)) size++
        return table[hash(key)].put(key, value)
    }

    /**
     * 删除键[key]，返回所对应的值
     */
    override fun remove(key: K): V? {
        val result = table[hash(key)].remove(key)
        if (result != null) size--
        if (size == 2 * capacity && capacity shr 1 >= DEFAULT_CAPACITY) resize(capacity shr 1)
        return result
    }

    /**
     * 返回键[key]对应的值
     * 运算符重载，允许使用下标访问字典
     */
    override fun get(key: K): V? {
        return table[hash(key)][key]
    }

    /**
     * 运算符重载，允许使用in判断键[key]是否在字典中
     */
    override fun contains(key: K): Boolean {
        return table[hash(key)].contains(key)
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
            for (t in table) {
                list.addAll(t.keys)
            }
            return list
        }

    private fun resize(newCapacity: Int) {
        val oldCapacity = capacity
        val oldTable = table
        capacity = newCapacity
        table = Array(newCapacity) { LinkedMap() }
        size = 0
        repeat(oldCapacity) {
            for ((k, v) in oldTable[it]) {
                put(k, v)
            }
        }
    }

    private fun hash(key: K): Int {
        return (key.hashCode() and 0x7fffffff) % capacity
    }
}