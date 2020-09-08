package graph

/**
 * dfs(Depth First Search)
 * 深度优先搜索
 */
fun Graph.dfs(): Iterable<Int> {
    val list = mutableListOf<Int>()
    val visited = BooleanArray(this.vertexCount)

    fun dfs(v: Int) {
        visited[v] = true
        list.add(v)
        for (w in adjOf(v)) {
            if (!visited[w]) dfs(w)
        }
    }

    for (v in 0 until this.vertexCount) {
        if (!visited[v]) dfs(v)
    }
    return list
}