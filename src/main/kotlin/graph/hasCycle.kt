package graph

/**
 * 无向图环检测
 */
fun Graph.hasCycle(): Boolean {
    val visited = BooleanArray(this.vertexCount)
    val prev = IntArray(this.vertexCount)
    var hasCycle = false

    fun dfs(v: Int) {
        visited[v] = true
        for (w in this.adjOf(v)) {
            if (!visited[w]) {
                prev[w] = v
                dfs(w)
            } else if (prev[v] != w) {
                hasCycle = true
                return
            }
        }
    }

    for (v in 0 until this.vertexCount) {
        if (hasCycle) break
        if (!visited[v]) dfs(v)
    }

    return hasCycle
}

/**
 * 有向图环检测
 */
fun Digraph.hasCycle(): Boolean {
    val visited = BooleanArray(this.vertexCount)
    val onPath = BooleanArray(this.vertexCount)
    var hasCycle = false

    fun dfs(v: Int) {
        visited[v] = true
        onPath[v] = true
        for (w in this.adjOf(v)) {
            if (!visited[w]) {
                dfs(w)
            } else if (onPath[w]) {
                hasCycle = true
                return
            }
        }
        onPath[v] = false
    }

    for (v in 0 until this.vertexCount) {
        if (hasCycle) break
        if (!visited[v]) dfs(v)
    }

    return hasCycle
}