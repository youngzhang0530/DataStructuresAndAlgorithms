package graph

/**
 * 前序深度优先搜索无向无权图
 */
fun Graph.preOrder(): Iterable<Int> {
    val order = ArrayDeque<Int>()
    val visited = BooleanArray(this.vertexCount)

    fun dfs(v: Int) {
        visited[v] = true
        order.addLast(v)
        for (w in adjOf(v)) {
            if (!visited[w]) dfs(w)
        }
    }

    for (v in 0 until this.vertexCount) {
        if (!visited[v]) dfs(v)
    }
    return order
}

/**
 * 后序深度优先搜索无向无权图
 */
fun Graph.postOrder(): Iterable<Int> {
    val order = ArrayDeque<Int>()
    val visited = BooleanArray(this.vertexCount)

    fun dfs(v: Int) {
        visited[v] = true
        for (w in adjOf(v)) {
            if (!visited[w]) dfs(w)
        }
        order.addLast(v)
    }

    for (v in 0 until this.vertexCount) {
        if (!visited[v]) dfs(v)
    }
    return order
}

/**
 * 逆后序深度优先搜索有向无权图
 */
fun Digraph.reversePostOrder(): Iterable<Int> {
    val order = ArrayDeque<Int>()
    val visited = BooleanArray(this.vertexCount)

    fun dfs(v: Int) {
        visited[v] = true
        for (w in adjOf(v)) {
            if (!visited[w]) dfs(w)
        }
        order.addFirst(v)
    }

    for (v in 0 until this.vertexCount) {
        if (!visited[v]) dfs(v)
    }

    return order
}

/**
 * 逆后序深度优先搜索有向有权图
 */
fun WeightedDigraph.reversePostOrder(): Iterable<Int> {
    val order = ArrayDeque<Int>()
    val visited = BooleanArray(this.vertexCount)

    fun dfs(v: Int) {
        visited[v] = true
        for (w in adjOf(v)) {
            if (!visited[w]) dfs(w)
        }
        order.addFirst(v)
    }

    for (v in 0 until this.vertexCount) {
        if (!visited[v]) dfs(v)
    }

    return order
}