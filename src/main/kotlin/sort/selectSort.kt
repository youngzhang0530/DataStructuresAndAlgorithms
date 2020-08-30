package sort

/**
 * 选择排序
 */
fun <E : Comparable<E>> Array<E>.selectSort() {

    fun exchange(a: Int, b: Int) {
        val t = this[a]
        this[a] = this[b]
        this[b] = t
    }

    for (i in this.indices) {
        var min = i
        for (j in (i + 1) until this.size) {
            if (this[min] > this[j]) min = j
        }
        exchange(i, min)
    }
}