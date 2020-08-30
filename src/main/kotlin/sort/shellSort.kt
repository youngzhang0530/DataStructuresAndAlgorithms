package sort

/**
 * 希尔排序
 */
fun <E : Comparable<E>> Array<E>.shellSort() {

    fun exchange(a: Int, b: Int) {
        val t = this[a]
        this[a] = this[b]
        this[b] = t
    }

    val n = this.size
    var h = 1
    while (h < n / 3) h = 3 * h + 1
    while (h >= 1) {
        for (i in h until this.size) {
            var j = i
            while (j >= h && this[j] < this[j - h]) {
                exchange(j, j - h)
                j--
            }
        }
        h /= 3
    }
}