package graph

import queue.IndexPriorityQueue

/**
 * Prim最小生成树算法
 */
fun WeightedGraph.primMST(): Iterable<Triple<Int, Int, Double>> {

    val mst = mutableListOf<Triple<Int, Int, Double>>()
    if (this.cc() > 1) return mst

    val visited = BooleanArray(this.vertexCount)
    val pq = IndexPriorityQueue<Triple<Int, Int, Double>>(this.vertexCount) { e1, e2 ->
        when {
            e1.third < e2.third -> -1
            e1.third > e2.third -> 1
            else -> 0
        }
    }

    fun visit(v: Int) {
        visited[v] = true
        for (w in this.adjOf(v)) {
            if (!visited[w]) {
                val weight = this.weightOf(v, w)
                if (w !in pq) pq.insert(w, Triple(v, w, weight))
                else if (weight < pq.peek(w).third) pq.change(w, Triple(v, w, weight))
            }
        }
    }

    visit(0)

    while (!pq.isEmpty()) {
        val (v, edge) = pq.remove()
        mst.add(edge)
        visit(v)
    }

    return mst
}