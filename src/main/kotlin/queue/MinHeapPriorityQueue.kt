package queue

@Suppress("UNCHECKED_CAST")
class MinHeapPriorityQueue<E : Comparable<E>> : Queue<E> {
    private companion object {
        const val DEFAULT_CAPACITY = 1 shl 4
    }

    override var size = 0
        private set
    private var capacity = DEFAULT_CAPACITY
    private var elements = arrayOfNulls<Any>(capacity)
    override fun add(e: E) {
        if (size == capacity) {
            capacity = capacity shl 1
            resize(capacity)
        }
        elements[size] = e
        swim(size)
        size++
    }

    override fun remove(): E {
        val result = elements[0] ?: throw NoSuchElementException()
        elements[0] = elements[size - 1]
        elements[size - 1] = null
        sink(0)
        size--
        if (size <= capacity shr 1 && capacity shr 1 >= DEFAULT_CAPACITY) {
            capacity = capacity shr 1
            resize(capacity)
        }
        return result as E
    }

    override fun peek(): E {
        return (elements[0] ?: throw NoSuchElementException()) as E
    }

    override fun isEmpty(): Boolean {
        return size == 0
    }

    private fun resize(newCapacity: Int) {
        elements = elements.copyOf(newCapacity)
    }

    private fun swim(k: Int) {
        val parent = (k - 1) / 2
        if ((elements[k] as E) < (elements[parent] as E)) {
            exchange(parent, k)
            if (parent > 0) swim(parent)
        }
    }

    private fun exchange(a: Int, b: Int) {
        val t = elements[a]
        elements[a] = elements[b]
        elements[b] = t
    }

    private fun sink(k: Int) {
        var child = 2 * k + 1
        if (child >= capacity) return
        if (elements[child] == null) return
        if (child + 1 < capacity && elements[child + 1] != null && (elements[child] as E) > (elements[child + 1] as E)) child++
        if (elements[k] as E > elements[child] as E) {
            exchange(k, child)
            sink(child)
        }
    }
}