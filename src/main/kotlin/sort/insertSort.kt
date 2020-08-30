package sort

/**
 * 插入排序
 */
fun <E : Comparable<E>> Array<E>.insertSort() {

    fun exchange(a: Int, b: Int) {
        val t = this[a]
        this[a] = this[b]
        this[b] = t
    }

    for (i in 1 until this.size) {
        var j = i
        while (j >= 1 && this[j] < this[j - 1]) {
            exchange(j, j - 1)
            j--
        }
    }
}