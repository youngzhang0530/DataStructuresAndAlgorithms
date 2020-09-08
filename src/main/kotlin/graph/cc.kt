package graph

/**
 * 深度优先搜索求解连通分量
 */
fun Graph.cc(): Int {
    val visited = IntArray(this.vertexCount) { -1 }
    var count = 0

    fun dfs(v: Int) {
        visited[v] = count
        for (w in this.adjOf(v)) {
            if (visited[w] == -1) dfs(w)
        }
    }

    for (v in 0 until this.vertexCount) {
        if (visited[v] == -1) {
            dfs(v)
            count++
        }
    }

    return count
}