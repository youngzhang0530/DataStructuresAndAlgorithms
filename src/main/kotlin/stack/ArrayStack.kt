package stack

@Suppress("UNCHECKED_CAST")
class ArrayStack<E> : Stack<E> {
    private companion object {
        const val DEFAULT_CAPACITY = 1 shl 4
    }

    private var capacity = DEFAULT_CAPACITY
    var elements = arrayOfNulls<Any>(capacity)

    override var size = 0

    override fun push(e: E) {
        if (size == capacity) resize(capacity shl 1)
        elements[size] = e
        size++
    }

    override fun pop(): E {
        if (size == 0) throw NoSuchElementException()
        val result = elements[size - 1]
        elements[size - 1] = null
        size--
        if (size == capacity shr 2 && capacity shr 2 >= DEFAULT_CAPACITY) resize(capacity shr 1)
        return result as E
    }

    override fun peek(): E {
        if (size == 0) throw NoSuchElementException()
        return elements[size - 1] as E
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    private fun resize(newCapacity: Int) {
        elements = elements.copyOf(newCapacity)
        capacity = newCapacity
    }
}