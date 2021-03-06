package graph

import queue.IndexPriorityQueue

/**
 * Dijkstra最短路径算法
 */
fun WeightedDigraph.dijkstraSP(from: Int, to: Int): Iterable<Int> {
    this.validate(from)
    this.validate(to)
    val distanceTo = DoubleArray(this.vertexCount) { Double.MAX_VALUE }
    val prev = IntArray(this.vertexCount) { -1 }

    val pq = IndexPriorityQueue<Double>(this.vertexCount)

    fun relax(v: Int) {
        for (w in this.adjOf(v)) {
            val weight = this.weightOf(v, w)
            if (weight + distanceTo[v] < distanceTo[w]) {
                distanceTo[w] = weight + distanceTo[v]
                prev[w] = v
                if (w !in pq) pq.insert(w, distanceTo[w])
                else pq.change(w, distanceTo[w])
            }
        }
    }

    distanceTo[from] = 0.0
    prev[from] = from
    pq.insert(from, distanceTo[from])
    while (!pq.isEmpty()) {
        val v = pq.remove().first
        if (v != to) relax(v)
    }

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