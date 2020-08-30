package sort

/**
 * 快速排序
 */
fun <E : Comparable<E>> Array<E>.quickSort() {
    fun exchange(a: Int, b: Int) {
        val t = this[a]
        this[a] = this[b]
        this[b] = t
    }

    fun partition(low: Int, high: Int): Int {
        var i = low
        var j = high + 1
        while (true) {
            while (this[++i] <= this[low]) if (i == high) break
            while (this[low] <= this[--j]) if (j == low) break
            if (i >= j) break
            exchange(i, j)
        }
        exchange(low, j)
        return j
    }

    fun sort(low: Int, high: Int) {
        val j = partition(low, high)
        if (low < j - 1) sort(low, j - 1)
        if (j + 1 < high) sort(j + 1, high)
    }

    sort(0, this.size - 1)
}