package graph

import set.DisjointSet
import java.util.*

/**
 * Kruskal最小生成树算法
 */
fun WeightedGraph.kruskalMst(): MutableList<Triple<Int, Int, Double>> {

    val mst = mutableListOf<Triple<Int, Int, Double>>()
    if (this.cc() > 1)
        return mst

    val edges = PriorityQueue<Triple<Int, Int, Double>> { e1, e2 ->
        when {
            e1.third - e2.third < 0 -> -1
            e1.third - e2.third > 0 -> 1
            else -> 0
        }
    } as Queue<Triple<Int, Int, Double>>

    for (v in 0 until this.vertexCount) {
        for (w in this.adjOf(v)) {
            if (v < w) {
                val weight = this.weightOf(v, w)
                edges.add(Triple(v, w, weight))
            }
        }
    }

    val set = DisjointSet(this.vertexCount)

    while (edges.isNotEmpty()) {
        val edge = edges.remove()
        if (!set.isConnected(edge.first, edge.second)) {
            mst.add(edge)
            set.union(edge.first, edge.second)
        }
    }

    return mst
}