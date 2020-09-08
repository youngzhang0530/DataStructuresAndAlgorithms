package graph

/**
 * 使用广度优先搜索查找图中[from]到[to]的路径
 */
fun Graph.bfsPath(from: Int, to: Int): Iterable<Int> {

    val prev = IntArray(this.vertexCount) { -1 }
    val queue = ArrayDeque<Int>()

    fun bfs(u: Int) {
        queue.addLast(u)
        prev[u] = u
        while (queue.isNotEmpty()) {
            val v = queue.removeFirst()
            for (w in this.adjOf(v)) {
                if (prev[w] == -1) {
                    queue.addLast(w)
                    prev[w] = v
                    if (w == to) return
                }
            }
        }
    }

    bfs(from)

    val path = ArrayDeque<Int>()
    if (prev[to] != -1) {
        path.addFirst(to)
        var u = to
        do {
            path.addFirst(prev[u])
            u = prev[u]
        } while (u != from)
    }
    return path
}