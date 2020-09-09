package graph

/**
 * 基于回溯法求解tsp(Traveling Salesman Problem,旅行商问题)
 */
fun WeightedGraph.tsp(): Iterable<Int> {
    var minPath = ArrayDeque<Int>()
    if (this.cc() > 1) return minPath
    var minLength = Double.MAX_VALUE

    val prev = IntArray(this.vertexCount) { -1 }
    val visited = BooleanArray(this.vertexCount)
    var left = this.vertexCount
    val start = 0

    fun dfs(v: Int) {
        visited[v] = true
        left--
        for (w in this.adjOf(v)) {
            if (!visited[w]) {
                prev[w] = v
                dfs(w)
            } else if (w == start && left == 0) {
                val path = ArrayDeque<Int>()
                path.addFirst(v)
                var u = v
                var length = this.weightOf(v, w)
                do {
                    length += this.weightOf(u, prev[u])
                    path.addFirst(prev[u])
                    u = prev[u]
                } while (u != start)
                path.addLast(start)
                if (length < minLength) {
                    minLength = length
                    minPath = path
                }
            }
        }
        visited[v] = false
        left++
    }

    dfs(start)

    return minPath
}