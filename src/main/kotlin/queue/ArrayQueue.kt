package queue

/**
 * 基于数组的循环队列
 *
 * @constructor 创建一个队列
 */
@Suppress("UNCHECKED_CAST")
class ArrayQueue<E> : Queue<E> {

    private companion object {
        const val DEFAULT_CAPACITY = 1 shl 4
    }

    private var head = 0
    private var tail = 0
    private var capacity = DEFAULT_CAPACITY
    private var elements = arrayOfNulls<Any>(capacity)

    /**
     * 队列中元素的数量
     */
    override val size: Int
        get() = when {
            tail > head -> tail - head
            tail < head -> tail - head + capacity
            else -> 0
        }

    /**
     * 向队尾添加元素
     */
    override fun add(e: E) {
        if (size == capacity - 1) resize(capacity shl 1)
        elements[tail] = e
        tail = (tail + 1 + capacity) % capacity
    }

    /**
     * 移除队首元素
     */
    override fun remove(): E {
        if (size == 0) throw NoSuchElementException()
        val result = elements[head]
        elements[head] = null
        head = (head + 1 + capacity) % capacity
        if (size == capacity shr 2 && capacity shr 2 >= DEFAULT_CAPACITY) resize(capacity shr 1)
        return result as E
    }

    /**
     * 返回队首元素
     */
    override fun peek(): E {
        if (size == 0) throw NoSuchElementException()
        return elements[head] as E
    }

    /**
     * 判断队列是否为空
     */
    override fun isEmpty(): Boolean {
        return size == 0
    }

    private fun resize(newCapacity: Int) {
        val newElements = arrayOfNulls<Any>(newCapacity)
        var i = head
        for (k in 0 until size) {
            newElements[k] = elements[i]
            i = (i + 1) % capacity
        }
        tail = size
        head = 0
        elements = newElements
        capacity = newCapacity
    }
}