package graph

/**
 * 无环有权图最长路径算法
 */
fun WeightedDigraph.acyclicLP(from: Int, to: Int): Iterable<Int> {
    this.validate(from)
    this.validate(to)
    val distanceTo = DoubleArray(this.vertexCount) { Double.MIN_VALUE }
    val prev = IntArray(this.vertexCount) { -1 }

    fun relax(v: Int) {
        for (w in this.adjOf(v)) {
            val weight = this.weightOf(v, w)
            if (weight + distanceTo[v] > distanceTo[w]) {
                distanceTo[w] = weight + distanceTo[v]
                prev[w] = v
            }
        }
    }

    distanceTo[from] = 0.0
    prev[from] = from

    for (v in this.topologicalOrder()) relax(v)

    val path = ArrayDeque<Int>()
    if (prev[to] != -1) {
        path.addFirst(to)
        var v = to
        do {
            path.addFirst(prev[v])
            v = prev[v]
        } while (v != from)
    }
    return path
}