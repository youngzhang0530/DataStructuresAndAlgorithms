package queue

/**
 * 基于最小堆的索引优先队列
 *
 * @constructor 创建一个索引优先队列
 */
@Suppress("UNCHECKED_CAST")
class IndexPriorityQueue<E>(
    private val capacity: Int,
    private val comparator: Comparator<E>? = null
) {

    /**
     * 队列中的元素数量
     */
    var size = 0
        private set

    private val index = IntArray(capacity) { -1 }
    private val rIndex = IntArray(capacity) { -1 }
    private val elements = arrayOfNulls<Any>(capacity)

    /**
     * 向队列中添加索引为[i]的元素[e]
     */
    fun insert(i: Int, e: E) {
        checkIndex(i)
        if (i in this) throw IllegalArgumentException("The index $i is already in the queue.")
        if (size == capacity) throw IllegalArgumentException("The queue is full.")
        index[i] = size
        rIndex[size] = i
        elements[size] = e
        if (comparator != null) swimWithComparator(size)
        else swimWithComparable(size)
        size++
    }

    /**
     * 修改索引[i]对应的元素为[e]
     */
    fun change(i: Int, e: E) {
        checkIndex(i)
        if (i !in this) throw IllegalArgumentException("The index $i isn't in the queue.")
        val t = index[i]
        elements[t] = e
        if (comparator != null) {
            swimWithComparator(t)
            sinkWithComparator(t)
        } else {
            swimWithComparable(t)
            sinkWithComparable(t)
        }
    }

    /**
     * 删除并返回队列中最小的元素及其对应的索引
     * 如果队列为空，则抛出[NoSuchElementException]异常
     */
    fun remove(): Pair<Int, E> {
        if (size == 0) throw NoSuchElementException()
        val resultIndex = rIndex[0]
        val resultE = elements[0] as E

        exchange(0, size - 1)
        index[rIndex[size - 1]] = -1
        rIndex[size - 1] = -1
        elements[size - 1] = null

        size--
        if (comparator != null) sinkWithComparator(0)
        else sinkWithComparable(0)

        return resultIndex to resultE
    }

    /**
     * 删除索引[i]对应的元素
     * 如果队列中没有该元素，则抛出[NoSuchElementException]异常
     */
    fun remove(i: Int): E {
        if (i !in this) throw NoSuchElementException()
        val t = index[i]
        val result = elements[t]

        exchange(index[i], size - 1)
        elements[size - 1] = null
        index[rIndex[size - 1]] = -1
        rIndex[size - 1] = -1

        if (comparator != null) {
            swimWithComparator(t)
            sinkWithComparator(t)
        } else {
            swimWithComparable(t)
            sinkWithComparable(t)
        }

        size--

        return result as E
    }

    /**
     * 运算符重载
     * 判断队列中是否存在索引为[i]的元素
     */
    operator fun contains(i: Int): Boolean {
        checkIndex(i)
        return index[i] != -1
    }

    /**
     * 查看队列中最小的元素及其索引
     * 如果队列为空，则抛出[NoSuchElementException]异常
     */
    fun peek(): Pair<Int, E> {
        if (size == 0) throw NoSuchElementException()
        return rIndex[0] to elements[0] as E
    }

    /**
     * 查看索引[i]对应的元素
     */
    fun peek(i: Int): E {
        checkIndex(i)
        if (i !in this) throw NoSuchElementException()
        return elements[index[i]] as E
    }

    /**
     * 判断队列是否为空
     */
    fun isEmpty(): Boolean {
        return size == 0
    }

    private fun checkIndex(i: Int) {
        if (i < 0 || i >= capacity) throw IndexOutOfBoundsException()
    }

    private fun exchange(a: Int, b: Int) {
        val e = elements[a]
        elements[a] = elements[b]
        elements[b] = e

        index[rIndex[a]] = b
        index[rIndex[b]] = a
        val t = rIndex[a]
        rIndex[a] = rIndex[b]
        rIndex[b] = t
    }

    private fun swimWithComparator(k: Int) {
        val parent = (k - 1) / 2
        if (elements[k] != null
            && comparator!!.compare(elements[k] as E, elements[parent] as E) < 0
        ) {
            exchange(parent, k)
            swimWithComparator(parent)
        }
    }


    private fun swimWithComparable(k: Int) {
        val parent = (k - 1) / 2
        if (elements[k] != null && (elements[k] as Comparable<E>) < (elements[parent] as E)) {
            exchange(parent, k)
            swimWithComparable(parent)
        }
    }

    private fun sinkWithComparator(k: Int) {
        var child = 2 * k + 1
        if (child >= capacity) return
        if (elements[child] == null) return
        if (child + 1 < capacity && elements[child + 1] != null
            && comparator!!.compare(elements[child] as E, elements[child + 1] as E) > 0
        ) child++
        if (comparator!!.compare(elements[k] as E, elements[child] as E) > 0) {
            exchange(k, child)
            sinkWithComparator(child)
        }
    }

    private fun sinkWithComparable(k: Int) {
        var child = 2 * k + 1
        if (child >= capacity) return
        if (elements[child] == null) return
        if (child + 1 < capacity && elements[child + 1] != null
            && (elements[child] as Comparable<E>) > (elements[child + 1] as E)
        ) child++
        if (elements[k] as Comparable<E> > elements[child] as E) {
            exchange(k, child)
            sinkWithComparable(child)
        }
    }
}