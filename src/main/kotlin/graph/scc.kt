package graph

/**
 * 使用Kosaraju算法计算有向图的强连通分量
 */
fun Digraph.scc(): Int {
    val rDigraph = this.reverse()
    val visited = IntArray(rDigraph.vertexCount) { -1 }
    var count = 0

    fun dfs(v: Int) {
        visited[v] = count
        for (w in this.adjOf(v)) {
            if (visited[w] == -1) dfs(w)
        }
    }

    for (v in rDigraph.reversePostOrder()) {
        if (visited[v] == -1) {
            dfs(v)
            count++
        }
    }

    return count
}