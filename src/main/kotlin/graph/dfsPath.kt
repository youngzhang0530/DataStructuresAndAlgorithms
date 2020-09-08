package graph

/**
 * 使用深度优先搜索查找图中[from]到[to]的路径
 */
fun Graph.dfsPath(from: Int, to: Int): Iterable<Int> {
    val prev = IntArray(this.vertexCount) { -1 }

    fun dfs(v: Int) {
        if (v == to) return
        for (w in this.adjOf(v)) {
            if (prev[w] == -1) {
                prev[w] = v
                dfs(w)
            }
        }
    }

    prev[from] = from
    dfs(from)

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