package graph

/**
 * 基于回溯法求解哈密顿回路
 */
fun Graph.hamiltonianCycle(): Iterable<Iterable<Int>> {
    val cycles = mutableListOf<ArrayDeque<Int>>()
    if (this.cc() > 1) return cycles

    val visited = BooleanArray(this.vertexCount)
    val prev = IntArray(this.vertexCount)
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
                do {
                    path.addFirst(prev[u])
                    u = prev[u]
                } while (u != start)
                cycles.add(path)
            }
        }
        visited[v] = false
        left++
    }

    dfs(start)

    return cycles
}