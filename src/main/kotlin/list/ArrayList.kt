package list

/**
 * 动态数组
 *
 * @constructor 创建一个动态数组
 */
@Suppress("UNCHECKED_CAST")
class ArrayList<E> : List<E> {
    private companion object {
        const val DEFAULT_CAPACITY = 1 shl 4
    }

    /**
     * 列表中元素的数量
     */
    override var size = 0
        private set

    private var capacity = DEFAULT_CAPACITY

    private var elements = arrayOfNulls<Any>(capacity)

    /**
     * 判断列表是否为空
     */
    override fun isEmpty(): Boolean {
        return size == 0
    }

    /**
     * 向列表中索引为[index]的位置添加元素[e]
     */
    override fun add(index: Int, e: E) {
        checkPositionIndex(index)
        if (size == capacity) resize(capacity shl 1)
        for (i in size - 1 downTo index + 1) {
            elements[i] = elements[i - 1]
        }
        elements[index] = e
        size++
    }

    /**
     * 向列表末尾添加元素[e]
     */
    override fun add(e: E) {
        add(size, e)
    }

    /**
     * 返回索引为[index]的元素
     */
    override fun get(index: Int): E {
        checkElementIndex(index)
        return elements[index] as E
    }

    /**
     * 修改索引为[index]的元素为[e]
     */
    override fun set(index: Int, e: E): E {
        checkElementIndex(index)
        val result = elements[index]
        elements[index] = e
        return result as E
    }

    /**
     * 判断列表中是否包含元素[e]
     */
    override fun contains(e: E): Boolean {
        for (i in elements) {
            if (i == e) return true
        }
        return false
    }

    /**
     * 返回元素[e]在列表中的索引，返回-1表示列表中不存在元素[e]
     */
    override fun indexOf(e: E): Int {
        for (i in elements.indices) {
            if (elements[i] == e) return i
        }
        return -1
    }

    /**
     * 删除索引[index]位置的元素
     */
    override fun removeAt(index: Int): E {
        checkElementIndex(index)
        val result = elements[index]
        for (i in index + 1 until size - 1) {
            elements[i - 1] = elements[i]
        }
        elements[size - 1] = null
        size--
        if (size == capacity shr 2 && capacity shr 2 >= DEFAULT_CAPACITY) resize(capacity shr 1)
        return result as E
    }

    /**
     * 删除元素[e]
     */
    override fun remove(e: E): Boolean {
        for (i in elements.indices) {
            if (elements[i] == e) {
                removeAt(i)
                return true
            }
        }
        return false
    }

    private fun resize(newCapacity: Int) {
        elements = elements.copyOf(newCapacity)
        capacity = newCapacity
    }

    private fun checkPositionIndex(index: Int) {
        if (index !in 0..size) throw IndexOutOfBoundsException("Index: $index, Size: $size")
    }

    private fun checkElementIndex(index: Int) {
        if (index !in 0 until size) throw IndexOutOfBoundsException("Index: $index, Size: $size")
    }
}