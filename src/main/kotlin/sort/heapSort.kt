package sort

/**
 * 堆排序
 */
fun <E : Comparable<E>> Array<E>.heapSort() {
    fun exchange(a: Int, b: Int) {
        val t = this[a]
        this[a] = this[b]
        this[b] = t
    }

    /**
     * 闭区间[low,high]
     */
    fun sink(low: Int, high: Int) {
        var child = 2 * low + 1
        if (child > high) return
        if (child + 1 <= high && this[child + 1] > this[child]) child++
        if (this[low] < this[child]) {
            exchange(low, child)
            sink(child, high)
        }
    }

    for (i in this.size / 2 - 1 downTo 0) {
        sink(i, this.size - 1)
    }

    for (i in this.size - 1 downTo 1) {
        exchange(0, i)
        sink(0, i - 1)
    }
}