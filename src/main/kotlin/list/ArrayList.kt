package list

@Suppress("UNCHECKED_CAST")
class ArrayList<E> : List<E> {
    private companion object {
        const val DEFAULT_CAPACITY = 1 shl 4
    }

    override var size = 0
        private set

    private var capacity = DEFAULT_CAPACITY

    private var elements = Array<Any?>(capacity) { null }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    override fun add(index: Int, e: E) {
        checkPositionIndex(index)
        if (size == capacity) resize(capacity shl 1)
        for (i in size - 1 downTo index + 1) {
            elements[i] = elements[i - 1]
        }
        elements[index] = e
        size++
    }

    override fun add(e: E) {
        add(size, e)
    }

    override fun get(index: Int): E {
        checkElementIndex(index)
        return elements[index] as E
    }

    override fun set(index: Int, e: E): E {
        checkElementIndex(index)
        val result = elements[index]
        elements[index] = e
        return result as E
    }

    override fun contains(e: E): Boolean {
        for (i in elements) {
            if (i == e) return true
        }
        return false
    }

    override fun indexOf(e: E): Int {
        for (i in elements.indices) {
            if (elements[i] == e) return i
        }
        return -1
    }

    override fun removeAt(index: Int): E {
        checkElementIndex(index)
        val result = elements[index]
        for (i in index + 1 until size - 1) {
            elements[i - 1] = elements[i]
        }
        elements[size - 1] = null
        size--
        if (size == capacity shr 1 && capacity shr 1 > DEFAULT_CAPACITY) resize(capacity shr 1)
        return result as E
    }

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