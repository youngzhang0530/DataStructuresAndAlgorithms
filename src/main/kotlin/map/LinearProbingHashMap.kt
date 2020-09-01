package map

/**
 * 基于线性探测法的哈希表
 */
@Suppress("UNCHECKED_CAST")
class LinearProbingHashMap<K, V> : Map<K, V> {
    private companion object {
        const val DEFAULT_CAPACITY = 1 shl 4
    }

    private var capacity = DEFAULT_CAPACITY

    override var size = 0
        private set
    private var keyArray = arrayOfNulls<Any>(capacity)
    private var valueArray = arrayOfNulls<Any>(capacity)

    /**
     * 运算符重载，允许使用下标访问
     */
    override fun set(key: K, value: V) {
        put(key, value)
    }

    /**
     * 向字典中插入键值对，键为[key]，值为[value]
     * 如果字典中键的数目发生变化，则返回true
     */
    override fun put(key: K, value: V): V? {
        val result: Any?
        if (size == capacity shr 1) resize(capacity shl 1)
        var index = hash(key)
        while (keyArray[index] != null && (keyArray[index] as K) != key) index = (index + 1) % capacity
        if (keyArray[index] == null) size++
        result = valueArray[index]
        keyArray[index] = key
        valueArray[index] = value
        return result as V?
    }

    /**
     * 删除键[key]，返回所对应的值
     */
    override fun remove(key: K): V? {
        var index = hash(key)
        while (keyArray[index] != null && (keyArray[index] as K) != key) index = (index + 1) % capacity
        val result = valueArray[index]
        if (keyArray[index] != null) {
            keyArray[index] = null
            valueArray[index] = null
            size--
        }
        if (size == capacity / 8 && capacity shr 1 >= DEFAULT_CAPACITY) resize(capacity shr 1)
        return result as V
    }

    /**
     * 返回键[key]对应的值
     * 运算符重载，允许使用下标访问字典
     */
    override fun get(key: K): V? {
        var index = hash(key)
        while (keyArray[index] != null && (keyArray[index] as K) != key) index = (index + 1) % capacity
        return valueArray[index] as V
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
            keyArray.forEach {
                if (it != null) list.add(it as K)
            }
            return list
        }

    private fun resize(newCapacity: Int) {
        val oldCapacity = capacity
        val oldKeyArray = keyArray
        val oldValueArray = valueArray
        capacity = newCapacity
        keyArray = Array(newCapacity) { null }
        valueArray = Array(newCapacity) { null }
        size = 0
        repeat(oldCapacity) {
            if (oldKeyArray[it] != null) put(oldKeyArray[it] as K, oldValueArray[it] as V)
        }
    }

    private fun hash(key: K): Int {
        return (key.hashCode() and 0x7fffffff) % capacity
    }
}