package stack

/**
 * 基于数组的栈
 *
 * @constructor 创建一个栈
 */
@Suppress("UNCHECKED_CAST")
class ArrayStack<E> : Stack<E> {
    private companion object {
        const val DEFAULT_CAPACITY = 1 shl 4
    }

    private var capacity = DEFAULT_CAPACITY
    var elements = arrayOfNulls<Any>(capacity)

    /**
     * 栈中元素数量
     */
    override var size = 0

    /**
     * 向栈顶添加元素[e]
     */
    override fun push(e: E) {
        if (size == capacity) resize(capacity shl 1)
        elements[size] = e
        size++
    }

    /**
     * 删除栈顶元素，并返回
     * 如果栈为空，则抛出[NoSuchElementException]异常
     */
    override fun pop(): E {
        if (size == 0) throw NoSuchElementException()
        val result = elements[size - 1]
        elements[size - 1] = null
        size--
        if (size == capacity shr 2 && capacity shr 2 >= DEFAULT_CAPACITY) resize(capacity shr 1)
        return result as E
    }

    /**
     * 返回栈顶元素
     * 如果栈为空，则抛出[NoSuchElementException]异常
     */
    override fun peek(): E {
        if (size == 0) throw NoSuchElementException()
        return elements[size - 1] as E
    }

    /**
     * 判断栈是否为空
     */
    override fun isEmpty(): Boolean {
        return size == 0
    }

    private fun resize(newCapacity: Int) {
        elements = elements.copyOf(newCapacity)
        capacity = newCapacity
    }
}