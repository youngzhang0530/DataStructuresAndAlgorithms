package graph

import queue.IndexPriorityQueue

/**
 * Dijkstra最短路径算法
 */
fun WeightedDigraph.dijkstraSP(from: Int, to: Int): Double {
    this.validate(from)
    this.validate(to)
    val visited = BooleanArray(this.vertexCount)
    val distanceTo = DoubleArray(this.vertexCount) { Double.MAX_VALUE }

    val pq = IndexPriorityQueue<Double>(this.vertexCount)

    fun relax(v: Int) {
        visited[v] = true
        for (w in this.adjOf(v)) {
            val weight = this.weightOf(v, w)
            if (!visited[w] && weight + distanceTo[v] < distanceTo[w]) {
                distanceTo[w] = weight + distanceTo[v]
                if (w !in pq) pq.insert(w, distanceTo[w])
                else pq.change(w, distanceTo[w])
            }
        }
    }

    distanceTo[from] = 0.0
    pq.insert(from, distanceTo[from])
    while (!pq.isEmpty()) {
        val v = pq.remove().first
        if (v != to) relax(v)
    }

    return distanceTo[to]
}