package graph

/**
 * bfs(Breadth First Search)
 * 深度优先搜索
 */
fun Graph.bfs(): Iterable<Int> {

    val visited = BooleanArray(this.vertexCount)
    val queue = ArrayDeque<Int>()
    val list = mutableListOf<Int>()

    fun bfs(u: Int) {
        queue.addLast(u)
        visited[u] = true
        while (queue.isNotEmpty()) {
            val v = queue.removeFirst()
            list.add(v)
            for (w in this.adjOf(v)) {
                if (!visited[w]) {
                    queue.addLast(w)
                    visited[w] = true
                }
            }
        }
    }

    for (v in 0 until this.vertexCount) {
        if (!visited[v]) {
            bfs(v)
        }
    }

    return list
}