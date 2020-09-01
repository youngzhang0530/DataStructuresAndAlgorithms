package sort

/**
 * 归并排序
 */

fun <E : Comparable<E>> Array<E>.mergeSort() {

    val aux = arrayOfNulls<Any>(this.size)

    @Suppress("UNCHECKED_CAST")
    fun merge(low: Int, mid: Int, high: Int) {
        for (p in low..high) {
            aux[p] = this[p]
        }
        var i = low
        var j = mid + 1
        for (k in low..high) {
            when {
                i > mid -> this[k] = aux[j++] as E
                j > high -> this[k] = aux[i++] as E
                aux[i] as E > aux[j] as E -> this[k] = aux[j++] as E
                else -> this[k] = aux[i++] as E
            }
        }
    }

    fun sort(low: Int, high: Int) {
        val mid = low + (high - low) / 2
        if (low < mid) sort(low, mid)
        if (mid + 1 < high) sort(mid + 1, high)
        merge(low, mid, high)
    }

    sort(0, this.size - 1)
}