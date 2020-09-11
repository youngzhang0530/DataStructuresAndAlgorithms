package queue

/**
 * 基于最小堆的优先队列
 * 相比直接的排序算法，优先队列在动态地增删元素时，仍能保持良好的性能
 *
 * @constructor 创建一个优先队列
 */
@Suppress("UNCHECKED_CAST")
class PriorityQueue<E : Comparable<E>> : Queue<E> {
    private companion object {
        const val DEFAULT_CAPACITY = 1 shl 4
    }

    /**
     * 队列中的元素数量
     */
    override var size = 0
        private set
    private var capacity = DEFAULT_CAPACITY
    private var elements = arrayOfNulls<Any>(capacity)

    /**
     * 向队列中添加元素[e]
     */
    override fun add(e: E) {
        if (size == capacity) {
            capacity = capacity shl 1
            resize(capacity)
        }
        elements[size] = e
        swim(size)
        size++
    }

    /**
     * 删除队列中最小的元素，并返回
     * 如果队列为空，则抛出[NoSuchElementException]异常
     */
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

    /**
     * 查看队列中最小的元素
     * 如果栈为空，则抛出[NoSuchElementException]异常
     */
    override fun peek(): E {
        return (elements[0] ?: throw NoSuchElementException()) as E
    }

    /**
     * 判断队列是否为空
     */
    override fun isEmpty(): Boolean {
        return size == 0
    }

    private fun resize(newCapacity: Int) {
        elements = elements.copyOf(newCapacity)
    }

    private fun swim(k: Int) {
        val parent = (k - 1) / 2
        if (parent == k) return
        if ((elements[k] as E) < (elements[parent] as E)) {
            exchange(parent, k)
            swim(parent)
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