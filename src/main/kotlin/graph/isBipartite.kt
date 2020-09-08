package graph

/**
 * 二分图检测
 */
fun Graph.isBipartite(): Boolean {

    val visited = BooleanArray(this.vertexCount)
    val colors = BooleanArray(this.vertexCount)
    var isBipartite = true

    fun dfs(v: Int) {
        visited[v] = true
        for (w in this.adjOf(v)) {
            if (!visited[w]) {
                colors[w] = !colors[v]
                dfs(w)
            } else if (colors[w] == colors[v]) {
                isBipartite = false
                return
            }
        }
    }

    for (v in 0 until this.vertexCount) {
        if (!isBipartite) break
        if (!visited[v]) dfs(v)
    }

    return isBipartite
}