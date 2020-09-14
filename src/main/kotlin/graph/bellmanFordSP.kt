package graph

/**
 * Bellman-Ford最短路径算法
 */
fun WeightedDigraph.bellmanFordSP(from: Int, to: Int): Iterable<Int> {
    this.validate(from)
    this.validate(to)

    val prev = IntArray(this.vertexCount) { -1 }
    val distanceTo = DoubleArray(this.vertexCount) { Double.MAX_VALUE }

    fun relax(v: Int): Boolean {
        var hasRelaxed = true
        for (w in this.adjOf(v)) {
            val weight = this.weightOf(v, w)
            if (distanceTo[v] != Double.MAX_VALUE && weight + distanceTo[v] < distanceTo[w]) {
                distanceTo[w] = weight + distanceTo[v]
                hasRelaxed = false
                prev[w] = v
            }
        }
        return hasRelaxed
    }

    distanceTo[from] = 0.0
    prev[from] = from

    repeat(this.vertexCount - 1) {
        for (v in 0 until this.vertexCount) relax(v)
    }
    val path = ArrayDeque<Int>()

    for (v in 0 until this.vertexCount) {
        if (!relax(v)) return path
    }

    path.addFirst(to)
    var u = to
    do {
        path.addFirst(prev[u])
        u = prev[u]
    } while (u != from)
    return path
}