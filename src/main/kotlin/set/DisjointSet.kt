package set

/**
 * 采用数组实现的并查集
 * 并查集是一种树型的数据结构,用于处理一些不相交集合（Disjoint Sets）的合并及查询问题
 */

/**
 * 创建一个初始集合数为[n]的并查集
 */
class DisjointSet(private val n: Int) {

    var size = n
        private set
    private val parent = IntArray(n) { it }
    private val sz = IntArray(n) { 1 }

    /**
     * 将[p]、[q]所在的集合进行合并
     */
    fun union(p: Int, q: Int) {
        validate(p)
        validate(q)
        val i = find(p)
        val j = find(q)
        if (i == j) return

        if (sz[i] < sz[j]) {
            parent[i] = j
            sz[j] += sz[i]
        } else {
            parent[j] = i
            sz[i] += sz[j]
        }
        size--
    }

    /**
     * 返回[p]所在的集合
     */
    fun find(p: Int): Int {
        validate(p)
        var q = parent[p]
        while (q != parent[q]) {
            q = parent[q]
        }
        return q
    }

    /**
     * 判断[p]和[q]是否在同一集合
     */
    fun isConnected(p: Int, q: Int): Boolean {
        validate(p)
        validate(q)
        return find(p) == find(q)
    }

    private fun validate(p: Int) {
        if (p < 0 || p >= n)
            throw IllegalArgumentException("The element isn't present in the set.")
    }
}